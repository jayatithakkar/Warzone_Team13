package com.APP.Project.UserInterface.layouts.commands;

import com.APP.Project.UserInterface.constants.specifications.ArgumentsSpecification;
import com.APP.Project.UserInterface.constants.specifications.CommandsSpecification;
import com.APP.Project.UserInterface.layouts.CommandLayout;
import com.APP.Project.UserInterface.models.CommandLineArgument;
import com.APP.Project.UserInterface.models.PredefinedUserCommands;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has all the commands entered by user during the GAME_PLAY game state.
 *
 * @author Raj Kumar Ramesh
 * @version 1.0
 */
public class GamePlayCommand implements CommandLayout {
    /**
     * The list of user commands entered during GAME_PLAY state of GameState
     */
    List<PredefinedUserCommands> d_userCommands;

    /**
     * Constructor sets the predefined user commands. Commands used to check structure of command enetered by user.
     */
    public GamePlayCommand() {
        d_userCommands = new ArrayList<>();

        PredefinedUserCommands l_userCommand;

        // Example of the command:
        // > gameplayer -add playername -remove playername
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("gameplayer");
        l_userCommand.setCommandSpecification(CommandsSpecification.AT_LEAST_ONE);
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
        l_userCommand.setGamePhaseMethodName("setPlayers");
        d_userCommands.add(l_userCommand);

        // Example of the command:
        // > assigncountries
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("assigncountries");
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE);
        l_userCommand.setGameEngineStartCommand(true);
        l_userCommand.setGamePhaseMethodName("assignCountries");
        d_userCommands.add(l_userCommand);

        // Example of the command:
        // > deploy countryID num
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("deploy");
        l_userCommand.setOrderCommand(true);
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        l_userCommand.setNumOfValues(2);
        d_userCommands.add(l_userCommand);

        // Example of the command:
        // > advance countrynamefrom countynameto numarmies
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("advance");
        l_userCommand.setOrderCommand(true);
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        l_userCommand.setNumOfValues(3);
        d_userCommands.add(l_userCommand);


        // Example of the command:
        // > bomb countryID
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("bomb");
        l_userCommand.setOrderCommand(true);
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        l_userCommand.setNumOfValues(1);
        d_userCommands.add(l_userCommand);

        // Example of the command:
        // > blockade countryID
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("blockade");
        l_userCommand.setOrderCommand(true);
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        l_userCommand.setNumOfValues(1);
        d_userCommands.add(l_userCommand);

        // Example of the command:
        // > airlift sourcecountryID targetcountryID numarmies
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("airlift");
        l_userCommand.setOrderCommand(true);
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        l_userCommand.setNumOfValues(3);
        d_userCommands.add(l_userCommand);

        // Example of the command:
        // > negotiate playerID
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("negotiate");
        l_userCommand.setOrderCommand(true);
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        l_userCommand.setNumOfValues(1);
        d_userCommands.add(l_userCommand);
    }

    /**
     * {@inheritDoc}
     *
     * @return Value of the list of user commands for this class.
     */
    @Override
    public List<PredefinedUserCommands> getUserCommands() {
        return this.d_userCommands;
    }
}
