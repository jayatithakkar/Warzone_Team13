package com.APP.Project.UserInterface.layouts.classes;

import com.APP.Project.UserInterface.layouts.ClassLayout;

import java.util.HashMap;
import java.util.Map;

// This class has classes for all commands that can be executed under MAP_EDITOR game state by the user
public class MapEditorClass implements ClassLayout {

    // Mapping commands to class objects
    private final Map<String, String> d_commandToServiceMap = new HashMap<>();

    // Default constructor to map command with file.
    public MapEditorClass() {
        d_commandToServiceMap.put("editcontinent",
                "com.APP.Project.UserCoreLogic.map_features.adapters.ContinentAdapter");
        d_commandToServiceMap.put("editcountry", "com.APP.Project.UserCoreLogic.map_features.adapters.CountryAdapter");
        d_commandToServiceMap.put("editneighbor",
                "com.APP.Project.UserCoreLogic.map_features.adapters.CountryNeighborAdapter");
        d_commandToServiceMap.put("editmap", "com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter");
        d_commandToServiceMap.put("validatemap",
                "com.APP.Project.UserCoreLogic.map_features.adapters.ValidateMapAdapter");
        d_commandToServiceMap.put("showmap", "com.APP.Project.UserCoreLogic.map_features.adapters.ShowMapAdapter");
        d_commandToServiceMap.put("savemap", "com.APP.Project.UserCoreLogic.map_features.adapters.SaveMapAdapter");
        d_commandToServiceMap.put("loadmap", "com.APP.Project.UserCoreLogic.map_features.adapters.LoadMapAdapter");
    }

    // Method to get and then return the value of mappings
    public Map<String, String> fetchMappings() {
        return d_commandToServiceMap;
    }
}