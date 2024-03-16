package com.APP.Project.UserCoreLogic.Container;

import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class finds the <code>Player</code> entity from the runtime engine.
 *
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
public class PlayerContainer {
    /**
     * Finds and returns the first player matching the specified name.
     * This method searches through the list of players and returns the first one
     * whose name exactly matches
     * the provided name. If no such player is found, an EntityNotFoundException is
     * thrown, indicating
     * the absence of a player with the specified name.
     *
     * @param playerName The name of the player to search for. This parameter should
     *                   not be null.
     * @return The Player object matching the specified name.
     * @throws EntityNotFoundException If no player with the given name can be
     *                                 found.
     */
    public Player findByPlayerName(String p_playerName) throws EntityNotFoundException {
        List<Player> l_filteredPlayerList = GamePlayEngine.getInstance().getPlayerList().stream()
                .filter(p_player -> p_player.getName().equals(p_playerName)).collect(Collectors.toList());
        if (l_filteredPlayerList.size() > 0)
            return l_filteredPlayerList.get(0);
        throw new EntityNotFoundException(String.format("'%s' player not found", p_playerName));
    }

    /**
     * Checks if a player with the specified name exists in the player list.
     * This method searches through the list of players to see if at least one
     * player has a name
     * that exactly matches the provided name.
     *
     * @param playerName The name of the player to search for. This parameter should
     *                   not be null.
     * @return true if at least one player with the specified name exists; false
     *         otherwise.
     */
    public boolean existByPlayerName(String p_playerName) {
        return GamePlayEngine.getInstance().getPlayerList().stream()
                .filter(p_player -> p_player.getName().equals(p_playerName)).count() == 1;
    }
}