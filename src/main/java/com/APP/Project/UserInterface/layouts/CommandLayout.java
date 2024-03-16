package com.APP.Project.UserInterface.layouts;

import com.APP.Project.UserInterface.models.PredefinedUserCommands;

import java.util.List;

/**
 * Interface that specifies the method or methods that need to be implemented for different command-layout classes. 
 * The classes that provide the possible commands in a given game state are known as the command-layout.
 *
 * @author Raj Kumar Ramesh
 * @version 1.0
 */
public interface CommandLayout {
    /**
     * Retrieves the inherited class's user command list. 
     * The list of user commands generated in the constructor or static block must be included in the inheriting class.
     *
     * @return the list of commands
     */
    public List<PredefinedUserCommands> getUserCommands();
}
