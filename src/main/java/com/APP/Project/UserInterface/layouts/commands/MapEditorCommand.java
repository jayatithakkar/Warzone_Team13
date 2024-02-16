package com.APP.Project.UserInterface.layouts.commands;

import com.APP.Project.UserInterface.constants.specifications.ArgumentSpecification;
import com.APP.Project.UserInterface.constants.specifications.CommandConfiguration;
import com.APP.Project.UserInterface.layouts.CommandLayout;
import com.APP.Project.UserInterface.models.CommandArgument;
import com.APP.Project.UserInterface.models.PredefinedCommandList;

import java.util.ArrayList;
import java.util.List;

public class MapEditorCommand implements CommandLayout {
    private final List<PredefinedCommandList> d_playerCommands;

    // Default constructor
    public MapEditorCommand() {
        d_playerCommands = new ArrayList<>();
        initializeCommands();
    }

    // This method configures and set up the commands based on the game state
    private void initializeCommands() {
        addCommandWithArguments("editcontinent", CommandConfiguration.AT_LEAST_ONE,
                new CommandArgument("add", 2, ArgumentSpecification.EQUAL),
                new CommandArgument("remove", 1, ArgumentSpecification.EQUAL));

        addCommandWithArguments("editcountry", CommandConfiguration.AT_LEAST_ONE,
                new CommandArgument("add", 2, ArgumentSpecification.EQUAL),
                new CommandArgument("remove", 1, ArgumentSpecification.EQUAL));

        addCommandWithArguments("editneighbor", CommandConfiguration.AT_LEAST_ONE,
                new CommandArgument("add", 2, ArgumentSpecification.EQUAL),
                new CommandArgument("remove", 2, ArgumentSpecification.EQUAL));

        addCommand("showmap", CommandConfiguration.CAN_RUN_ALONE);
        addCommand("savemap", CommandConfiguration.CAN_RUN_ALONE_WITH_VALUE);
        addCommand("editmap", CommandConfiguration.CAN_RUN_ALONE_WITH_VALUE);
        addCommand("validatemap", CommandConfiguration.CAN_RUN_ALONE);
        addCommand("loadmap", CommandConfiguration.CAN_RUN_ALONE_WITH_VALUE);
    }

    // Method to confgure commands with arguments
    private void addCommandWithArguments(String headCommand, CommandConfiguration config,
            CommandArgument... arguments) {
        PredefinedCommandList command = new PredefinedCommandList();
        command.setHeadCommand(headCommand);
        command.setCommandConfig(config);
        for (CommandArgument argument : arguments) {
            command.pushCommandArgument(argument);
        }
        d_playerCommands.add(command);
    }

    // Method to confgure commands without arguments
    private void addCommand(String headCommand, CommandConfiguration config) {
        PredefinedCommandList command = new PredefinedCommandList();
        command.setHeadCommand(headCommand);
        command.setCommandConfig(config);
        d_playerCommands.add(command);
    }

    // Get and list predefined commands
    @Override
    public List<PredefinedCommandList> fetchUserCommands() {
        return d_playerCommands;
    }
}
