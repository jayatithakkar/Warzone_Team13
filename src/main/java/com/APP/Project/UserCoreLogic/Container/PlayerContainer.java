package com.APP.Project.UserCoreLogic.Container;

import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.gamePlay.GameEngine;

import java.util.*;
import java.util.stream.Collectors;

/*
 * This class is responsible for searching player from game engine.
 * @author Bhoomiben Bhatt
 */
public class PlayerContainer {
    /*
     * finds player by it's name
     */
    public Player searchByPlayerName(String p_playerName) throws EntityNotFoundException {
        /*
         * p_playerName is value of the name of the player.
         * returns the value of first matched player
         * if searching player not found then it will thrown a searching entity not
         * found exception.
         */
        List<Player> l_filteredPlayerList = GameEngine.getInstance().getPlayerList().stream()
                .filter(p_player -> p_player.getName().equals(p_playerName)).collect(Collectors.toList());
        if (l_filteredPlayerList.size() > 0)
            return l_filteredPlayerList.get(0);
        throw new EntityNotFoundException(String.format("'%s' player not found", p_playerName));
    }

    /*
     * this method is responsible for searching if player name already exists or
     * not.
     * p_playerName is name of searching player
     * returns false if player isn't exists already. otherwise true.
     */
    public boolean existByPlayerName(String p_playerName) {
        return GameEngine.getInstance().getPlayerList().stream()
                .filter(p_player -> p_player.getName().equals(p_playerName)).count() == 1;
    }
}