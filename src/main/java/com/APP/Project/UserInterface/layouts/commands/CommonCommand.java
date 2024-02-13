package com.APP.Project.UserInterface.layouts.commands;

import com.APP.Project.UserInterface.constants.specifications.CommandConfiguration;
import com.APP.Project.UserInterface.layouts.CommandLayout;
import com.APP.Project.UserInterface.models.PredefinedCommandList;

import java.util.ArrayList;
import java.util.List;

public class CommonCommand implements CommandLayout {
    List<PredefinedCommandList> d_playerCommands;

    // Default constructor
    public CommonCommand() {
        d_playerCommands = new ArrayList<>();
        addCommand("exit", CommandConfiguration.CAN_RUN_ALONE);
    }
    //  Configuration of the PlayerCommands
    private void addCommand(String headCommand, CommandConfiguration config) {
        PredefinedCommandList command = new PredefinedCommandList(headCommand, config);
        d_playerCommands.add(command);
    }

    // return value of the list of user commands for this class.
    @Override
    public List<PredefinedCommandList> fetchUserCommands() {
        return this.d_playerCommands;
    }
}
