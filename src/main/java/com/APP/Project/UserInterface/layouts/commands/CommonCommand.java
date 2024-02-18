package com.APP.Project.UserInterface.layouts.commands;

import com.APP.Project.UserInterface.constants.specifications.CommandsSpecification;
import com.APP.Project.UserInterface.layouts.CommandLayout;
import com.APP.Project.UserInterface.models.PredefinedUserCommands;

import java.util.ArrayList;
import java.util.List;

public class CommonCommand implements CommandLayout {
    List<PredefinedUserCommands> d_playerCommands;

    // Default constructor
    public CommonCommand() {
        d_playerCommands = new ArrayList<>();
        addCommand("exit", CommandsSpecification.RUN_ALONE);
        addCommand("loadmap", CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
    }
    //  Configuration of the PlayerCommands
    private void addCommand(String headCommand, CommandsSpecification config) {
        PredefinedUserCommands command = new PredefinedUserCommands();
        command.setHeadCommand(headCommand);
        command.setCommandSpecification(config);
        d_playerCommands.add(command);
    }

    // return value of the list of user commands for this class.
    @Override
    public List<PredefinedUserCommands> fetchUserCommands() {
        return this.d_playerCommands;
    }
}
