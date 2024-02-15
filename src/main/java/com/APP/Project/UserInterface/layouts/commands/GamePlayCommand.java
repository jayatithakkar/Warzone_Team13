package com.APP.Project.UserInterface.layouts.commands;

import com.APP.Project.UserInterface.constants.specifications.ArgumentSpecification;
import com.APP.Project.UserInterface.constants.specifications.CommandConfiguration;
import com.APP.Project.UserInterface.layouts.CommandLayout;
import com.APP.Project.UserInterface.models.CommandArgument;
import com.APP.Project.UserInterface.models.PredefinedCommandList;

import java.util.ArrayList;
import java.util.List;

// This class encompasses all the commands which can be entered by the user during the gameplay game state.

public class GamePlayCommand implements CommandLayout {
    private final List<PredefinedCommandList> d_playerCommands;

    // Default constructor
    public GamePlayCommand() {
        d_playerCommands = new ArrayList<>();
        initializeCommands();
    }

    // Methods initializeCommands and addCommand
    // used to set up the configuration based on the user commands with respect to
    // the game state
    private void initializeCommands() {
        addCommand("showmap", CommandConfiguration.CAN_RUN_ALONE, false, false, null);

        List<CommandArgument> gameplayerArgs = new ArrayList<>();
        gameplayerArgs.add(new CommandArgument("add", 1, ArgumentSpecification.EQUAL));
        gameplayerArgs.add(new CommandArgument("remove", 1, ArgumentSpecification.EQUAL));
        addCommand("gameplayer", CommandConfiguration.AT_LEAST_ONE, false, false, gameplayerArgs);

        addCommand("assigncountries", CommandConfiguration.CAN_RUN_ALONE, true, false, null);

        addCommand("deploy", CommandConfiguration.CAN_RUN_ALONE_WITH_VALUE, false, true, null);
    }

    private void addCommand(String headCommand, CommandConfiguration config, boolean isEngineStart,
            boolean isEngineCommand, List<CommandArgument> arguments) {
        PredefinedCommandList command = new PredefinedCommandList();
        command.setHeadCommand(headCommand);
        command.setCommandConfig(config);
        command.setGameEngineStartCommand(isEngineStart);
        command.setGameEngineCommand(isEngineCommand);

        if (arguments != null) {
            arguments.forEach(command::pushCommandArgument);
        }

        d_playerCommands.add(command);
    }

    // Get and list predefined commands
    @Override
    public List<PredefinedCommandList> fetchUserCommands() {
        return d_playerCommands;
    }
}
