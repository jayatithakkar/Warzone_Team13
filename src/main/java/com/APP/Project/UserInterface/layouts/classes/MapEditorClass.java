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
                "com.APP.Project.VirtualMachine.map_editor.services.ContinentService");
        d_commandToServiceMap.put("editcountry", "com.APP.Project.VirtualMachine.map_editor.services.CountryService");
        d_commandToServiceMap.put("editneighbor",
                "com.APP.Project.VirtualMachine.map_editor.services.CountryNeighborService");
        d_commandToServiceMap.put("editmap", "com.APP.Project.VirtualMachine.map_editor.services.EditMapService");
        d_commandToServiceMap.put("validatemap",
                "com.APP.Project.VirtualMachine.map_editor.services.ValidateMapService");
        d_commandToServiceMap.put("showmap", "com.APP.Project.VirtualMachine.map_editor.services.ShowMapService");
        d_commandToServiceMap.put("savemap", "com.APP.Project.VirtualMachine.map_editor.services.SaveMapService");
        d_commandToServiceMap.put("loadmap", "com.APP.Project.VirtualMachine.map_editor.services.LoadMapService");
    }

    // Method to get and then return the value of mappings
    public Map<String, String> fetchMappings() {
        return d_commandToServiceMap;
    }
}
