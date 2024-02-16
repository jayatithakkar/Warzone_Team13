package com.APP.Project.UserCoreLogic.containers;

import com.APP.Project.UserCoreLogic.GameEntities.Player;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.gamePlay.GameEngine;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerContainer {
    public Player searchByPlayerName(String p_playerName) throws EntityNotFoundException {
        List<Player> l_newfilteredPlayerList = GameEngine.getInstance().getPlayerList().stream()
                .filter(p_player -> p_player.getName().equals(p_playerName)).collect(Collectors.toList());
        if (l_newfilteredPlayerList.size() > 0)
            return l_newfilteredPlayerList.get(0);
        throw new EntityNotFoundException(String.format("'%s' player not found", p_playerName));
    }

    public boolean existByPlayerName(String p_playerName) {
        return GameEngine.getInstance().getPlayerList().stream()
                .filter(p_player -> p_player.getName().equals(p_playerName)).count() == 1;
    }
}