package com.APP.Project.UserInterface.layouts.classes;

import com.APP.Project.UserInterface.layouts.ClassLayout;

import java.util.HashMap;
import java.util.Map;

// This class has layouts for which the UserInterface can be used to refer any of game states
public class CommonClass implements ClassLayout {

    // Mapping commands to class objects
    private final Map<String, String> d_commandToServiceMap = new HashMap<>();

    // Default constructor to map command with file.

    public CommonClass() {
        d_commandToServiceMap.put("exit", "com.APP.Project.VirtualMachine.common.services.ApplicationExitService");
    }

    // Method to get and then return the value of mappings
    public Map<String, String> fetchMappings() {
        return d_commandToServiceMap;
    }
}
