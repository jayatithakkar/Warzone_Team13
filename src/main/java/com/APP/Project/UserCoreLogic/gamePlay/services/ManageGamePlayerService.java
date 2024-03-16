package com.APP.Project.UserCoreLogic.gamePlay.services;

import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;
import com.APP.Project.UserCoreLogic.logger.LogEntryBuffer;
import com.APP.Project.UserCoreLogic.Container.PlayerContainer;

/**
 * This class handles `gameplayer` user command to add and/or remove game player from the game.
 *
 * @author Brijesh Lakkad
 * @author MILESH
 * @version 1.0
 */
public class ManageGamePlayerService {

    /**
     * Engine to store and retrieve map data.
     */
    private final GamePlayEngine d_gamePlayEngine;

    /**
     * Player repository.
     */
    private final PlayerContainer d_playerRepository;

    private final LogEntryBuffer d_logEntryBuffer;

    /**
     * Initialization of different objects.
     */
    public ManageGamePlayerService() {
        d_gamePlayEngine = GamePlayEngine.getInstance();
        d_playerRepository = new PlayerContainer();
        d_logEntryBuffer = LogEntryBuffer.getLogger();
    }

    /**
     * Adds the player to the list stored at Game Play engine.
     *
     * @param p_playerName Value of the player name.
     * @return Value of response of the request.
     * @throws InvalidInputException Throws if processing the player creation.
     */
    public String add(String p_playerName) throws InvalidInputException {
        if (!d_playerRepository.existByPlayerName(p_playerName)) {
            try {
                Player l_player = new Player();
                l_player.setName(p_playerName);
                d_gamePlayEngine.addPlayer(l_player);
                d_logEntryBuffer.dataChanged("gameplayer", "\n---GAMEPLAYER---\n" + p_playerName + " player added!\n");
                return String.format("%s player added!", p_playerName);
            } catch (Exception e) {
                throw new InvalidInputException("Player name is not valid");
            }
        } else {
            throw new InvalidInputException("Player name already exists....Please provide different name.");
        }
    }

    /**
     * Removes the player from the list using the name.
     *
     * @param p_playerName Value of the continent name.
     * @return Value of response of the request.
     * @throws EntityNotFoundException If the player with provided name not found.
     */
    public String remove(String p_playerName) throws EntityNotFoundException {
        // We can check if the continent exists before filtering?
        // Filters the continent list using the continent name
        Player l_player = d_playerRepository.findByPlayerName(p_playerName);
        d_gamePlayEngine.removePlayer(l_player);
        d_logEntryBuffer.dataChanged("gameplayer", "\n---GAMEPLAYER---\n" + p_playerName + " player removed!\n");
        return String.format("%s player removed!", p_playerName);
    }
}
