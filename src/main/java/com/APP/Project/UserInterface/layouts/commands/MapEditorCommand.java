package com.APP.Project.UserInterface.layouts.commands;

import com.APP.Project.UserInterface.constants.specifications.ArgumentsSpecification;
import com.APP.Project.UserInterface.constants.specifications.CommandsSpecification;
import com.APP.Project.UserInterface.layouts.CommandLayout;
import com.APP.Project.UserInterface.models.CommandLineArgument;
import com.APP.Project.UserInterface.models.PredefinedUserCommands;

import java.util.ArrayList;
import java.util.List;

public class MapEditorCommand implements CommandLayout {
    private final List<PredefinedUserCommands> d_playerCommands;

    // Default constructor
    public MapEditorCommand() {
        d_playerCommands = new ArrayList<>();
        initializeCommands();
    }

    // This method configures and set up the commands based on the game state
    private void initializeCommands() {
        addCommandWithArguments("editcontinent", CommandsSpecification.NEED_ONE,
                new CommandLineArgument("add", 2, ArgumentsSpecification.EQUAL),
                new CommandLineArgument("remove", 1, ArgumentsSpecification.EQUAL));

        addCommandWithArguments("editcountry", CommandsSpecification.NEED_ONE,
                new CommandLineArgument("add", 2, ArgumentsSpecification.EQUAL),
                new CommandLineArgument("remove", 1, ArgumentsSpecification.EQUAL));

        addCommandWithArguments("editneighbor", CommandsSpecification.NEED_ONE,
                new CommandLineArgument("add", 2, ArgumentsSpecification.EQUAL),
                new CommandLineArgument("remove", 2, ArgumentsSpecification.EQUAL));

        addCommand("showmap", CommandsSpecification.RUN_ALONE);
        addCommand("savemap", CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        addCommand("editmap", CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        addCommand("validatemap", CommandsSpecification.RUN_ALONE);
        addCommand("loadmap", CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
    }

    // Method to confgure commands with arguments
    private void addCommandWithArguments(String headCommand, CommandsSpecification config,
            CommandLineArgument... arguments) {
        PredefinedUserCommands command = new PredefinedUserCommands();
        command.setHeadCommand(headCommand);
        command.setCommandSpecification(config);
        for (CommandLineArgument argument : arguments) {
            command.pushCommandArgument(argument);
        }
        d_playerCommands.add(command);
    }

    // Method to confgure commands without arguments
    private void addCommand(String headCommand, CommandsSpecification config) {
        PredefinedUserCommands command = new PredefinedUserCommands();
        command.setHeadCommand(headCommand);
        command.setCommandSpecification(config);
        d_playerCommands.add(command);
    }

    // Get and list predefined commands
    @Override
    public List<PredefinedUserCommands> fetchUserCommands() {
        return d_playerCommands;
    }
}
