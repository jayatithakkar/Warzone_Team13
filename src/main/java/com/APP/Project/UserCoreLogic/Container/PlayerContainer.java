package com.APP.Project.UserCoreLogic.Container;

import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.Exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.gamePlay.GameEngine;

import java.util.*;
import java.util.stream.Collectors;

/*
 * This class is responsible for searching player from game engine.
 * 
 * @author Bhoomiben Bhatt
 * @verson 1.0
 */
public class PlayerContainer {

    /**
     * finds player by it's name
     * 
     * @param p_playerName is value of the name of the player.
     * @return the value of first matched player
     * @throws EntityNotFoundException if searching player not found
     */
    public Player searchByPlayerName(String p_playerName) throws EntityNotFoundException {

        List<Player> l_filteredPlayerList = GameEngine.getInstance().getPlayerList().stream()
                .filter(p_player -> p_player.getName().equals(p_playerName)).collect(Collectors.toList());
        if (l_filteredPlayerList.size() > 0)
            return l_filteredPlayerList.get(0);
        throw new EntityNotFoundException(String.format("'%s' player not found", p_playerName));
    }

    /**
     * this method is responsible for searching if player name already exists or
     * not.
     * 
     * @param p_playerName is name of searching player
     * @return false if player isn't exists already. True otherwise.
     */
    public boolean existByPlayerName(String p_playerName) {
        return GameEngine.getInstance().getPlayerList().stream()
                .filter(p_player -> p_player.getName().equals(p_playerName)).count() == 1;
    }
}