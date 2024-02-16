package com.APP.Project.UserCoreLogic.gamePlay.services;

import com.APP.Project.UserCoreLogic.GameEntities.Player;
import com.APP.Project.UserCoreLogic.containers.PlayerContainer;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.gamePlay.GameEngine;

public class ManageGamePlayerService {

    private final PlayerContainer d_gamePlayerContainer;


    private final GameEngine d_GameEngine;
    /**
     * Default constructor to initialise all objects
     */
    public ManageGamePlayerService() {
        d_GameEngine = GameEngine.getInstance();
        d_gamePlayerContainer = new PlayerContainer();
    }

    /**
     * This method removes the player from the list by using the passed name.
     *
     * @param p_gamePlayerName Value of the continent name.
     * @return value of request response.
     * @throws EntityNotFoundException Is thrown if the player with given name is not found.
     */
    public String removePlayer(String p_gamePlayerName) throws EntityNotFoundException {
        Player l_gamePlayer = d_gamePlayerContainer.findByPlayerName(p_gamePlayerName);
        d_GameEngine.removePlayer(l_gamePlayer);
        return String.format("The player %s has been removed.", p_gamePlayerName);
    }

    /**
     * This method adds the player to the list stored at Game Play engine.
     *
     * @param p_gamePlayerName value of the player name.
     * @return value of request response.
     * @throws InvalidInputException is thrown in case of error in processing of player creation.
     */
    public String addPlayer(String p_gamePlayerName) throws InvalidInputException {
        if (!d_gamePlayerContainer.existByPlayerName(p_gamePlayerName)) {
            try {
                Player l_gamePlayer = new Player();
                l_gamePlayer.setName(p_gamePlayerName);
                d_GameEngine.addPlayer(l_gamePlayer);
                return String.format("The player %s has been added", p_gamePlayerName);
            } catch (Exception e) {
                throw new InvalidInputException("The player name is not valid");
            }
        } else {
            throw new InvalidInputException("Kindly provide different name. Player name already exists.");
        }
    }

}

