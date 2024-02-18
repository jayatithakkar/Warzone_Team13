package com.APP.Project.UserInterface.layouts.classes;

import com.APP.Project.UserInterface.layouts.ClassLayout;

import java.util.HashMap;
import java.util.Map;

// This class has classes for all commands that can be executed under the Gameplay state

public class GamePlayClass implements ClassLayout {

    // Mapping commands to class objects
    private final Map<String, String> d_commandToServiceMap = new HashMap<>();

    // Default constructor to map command with file.
    public GamePlayClass() {
        d_commandToServiceMap.put("gameplayer", "com.APP.Project.UserCoreLogic.gamePlay.services.ManageGamePlayerService");
        d_commandToServiceMap.put("assigncountries",
                "com.APP.Project.UserCoreLogic.gamePlay.services.CountryDistributionService");
        d_commandToServiceMap.put("showmap", "com.APP.Project.UserCoreLogic.gamePlay.services.DisplayMapService");
    }

    // Method to get and then return the value of mappings
    public Map<String, String> fetchMappings() {
        return d_commandToServiceMap;
    }
}
