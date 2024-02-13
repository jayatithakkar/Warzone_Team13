package com.APP.Project.UserInterface.layouts;

import com.APP.Project.UserInterface.models.PredefinedCommandList;

import java.util.List;

//Interface to define the method which should be implemented for various command-layout classes. The command-layout are the classes that define the available commands throughout a game state.

public interface CommandLayout {

    // Fetches and returns the list of user commands
    public List<PredefinedCommandList> fetchUserCommands();
}
