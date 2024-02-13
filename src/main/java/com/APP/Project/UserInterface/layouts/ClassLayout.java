package com.APP.Project.UserInterface.layouts;

import java.util.Map;

// The interface specifies the method(s) that must be implemented for different class-layout classes. 
// The class-layout refers to the classes that define the precise commands for a certain game state.
public interface ClassLayout {

    // Fetches the command mappings to their corresponding Java classes.
    // The list of predefined classes for a given command must be in the inheriting
    // class.

    public Map<String, String> fetchMappings();
}
