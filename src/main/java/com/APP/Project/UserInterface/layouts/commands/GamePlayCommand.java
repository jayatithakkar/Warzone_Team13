package com.APP.Project.UserInterface.layouts.commands;

import com.APP.Project.UserInterface.constants.specifications.ArgumentsSpecification;
import com.APP.Project.UserInterface.constants.specifications.CommandsSpecification;
import com.APP.Project.UserInterface.layouts.CommandLayout;
import com.APP.Project.UserInterface.models.CommandLineArgument;
import com.APP.Project.UserInterface.models.PredefinedUserCommands;

import java.util.ArrayList;
import java.util.List;

// This class encompasses all the commands which can be entered by the user during the gameplay game state.

public class GamePlayCommand implements CommandLayout {
    List<PredefinedUserCommands> d_playerCommands;

    // Default constructor
    public GamePlayCommand() {
        d_playerCommands = new ArrayList<>();

        PredefinedUserCommands l_userCommand;
        // Example of the command:
        // > showmap
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("showmap");
        l_userCommand.setCommandSpecification(CommandsSpecification.RUN_ALONE);
        d_playerCommands.add(l_userCommand);

        // Example of the command:
        // > gameplayer -add playername -remove playername
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("gameplayer");
        l_userCommand.setCommandSpecification(CommandsSpecification.NEED_ONE);
        l_userCommand.pushCommandArgument(new CommandLineArgument(
                "add",
                1,
                ArgumentsSpecification.EQUAL
        ));
        l_userCommand.pushCommandArgument(new CommandLineArgument(
                "remove",
                1,
                ArgumentsSpecification.EQUAL
        ));
        d_playerCommands.add(l_userCommand);

        // Example of the command:
        // > assigncountries
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("assigncountries");
        l_userCommand.setCommandSpecification(CommandsSpecification.RUN_ALONE);
        l_userCommand.setGameEngineStartCommand(true);
        d_playerCommands.add(l_userCommand);

        // Example of the command:
        // > deploy countryID num
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("deploy");
        l_userCommand.setGameEngineCommand(true);
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        d_playerCommands.add(l_userCommand);
    }

    // Methods initializeCommands and addCommand
    // used to set up the configuration based on the user commands with respect to
    // the game state
    private void initializeCommands() {
        addCommand("showmap", CommandsSpecification.RUN_ALONE, false, false, null);

        List<CommandLineArgument> gameplayerArgs = new ArrayList<>();
        gameplayerArgs.add(new CommandLineArgument("add", 1, ArgumentsSpecification.EQUAL));
        gameplayerArgs.add(new CommandLineArgument("remove", 1, ArgumentsSpecification.EQUAL));
        addCommand("gameplayer", CommandsSpecification.NEED_ONE, false, false, gameplayerArgs);

        addCommand("assigncountries", CommandsSpecification.RUN_ALONE, true, false, null);

        addCommand("deploy", CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE, false, true, null);
    }

    private void addCommand(String headCommand, CommandsSpecification config, boolean isEngineStart,
            boolean isEngineCommand, List<CommandLineArgument> arguments) {
        PredefinedUserCommands command = new PredefinedUserCommands();
        command.setHeadCommand(headCommand);
        command.setCommandSpecification(config);
        command.setGameEngineStartCommand(isEngineStart);
        command.setGameEngineCommand(isEngineCommand);

        if (arguments != null) {
            arguments.forEach(command::pushCommandArgument);
        }

        d_playerCommands.add(command);
    }

    // Get and list predefined commands
    @Override
    public List<PredefinedUserCommands> fetchUserCommands() {
        return d_playerCommands;
    }
}
