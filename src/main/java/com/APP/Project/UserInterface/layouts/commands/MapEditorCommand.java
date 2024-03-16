package com.APP.Project.UserInterface.layouts.commands;

import com.APP.Project.UserInterface.constants.specifications.ArgumentsSpecification;
import com.APP.Project.UserInterface.constants.specifications.CommandsSpecification;
import com.APP.Project.UserInterface.layouts.CommandLayout;
import com.APP.Project.UserInterface.models.CommandLineArgument;
import com.APP.Project.UserInterface.models.PredefinedUserCommands;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has all the commands entered by user during the MAP_EDITOR game state.
 *
 * @author Raj Kumar Ramesh
 * @version 1.0
 */
public class MapEditorCommand implements CommandLayout {
    /**
    * The list of user commands entered during MAP_EDITOR state of GameState
     */
    List<PredefinedUserCommands> d_userCommands;

    /**
     * Constructor sets the predefined user commands. Commands used to check structure of command enetered by user.
     */
    public MapEditorCommand() {
        d_userCommands = new ArrayList<>();

        // Example of the below command:
        // > editcontinent -add continentID continentvalue -remove continentID
        PredefinedUserCommands l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("editcontinent");
        l_userCommand.setCommandSpecification(CommandsSpecification.AT_LEAST_ONE);
        l_userCommand.pushCommandArgument(new CommandLineArgument(
                "add",
                2,
                ArgumentsSpecification.EQUAL
        ));
        l_userCommand.pushCommandArgument(new CommandLineArgument(
                "remove",
                1,
                ArgumentsSpecification.EQUAL
        ));
        l_userCommand.setGamePhaseMethodName("editContinent");
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > editcountry -add countryID continentID -remove countryID
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("editcountry");
        l_userCommand.setCommandSpecification(CommandsSpecification.AT_LEAST_ONE);
        l_userCommand.pushCommandArgument(new CommandLineArgument(
                "add",
                2,
                ArgumentsSpecification.EQUAL
        ));
        l_userCommand.pushCommandArgument(new CommandLineArgument(
                "remove",
                1,
                ArgumentsSpecification.EQUAL
        ));
        l_userCommand.setGamePhaseMethodName("editCountry");
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("editneighbor");
        l_userCommand.setCommandSpecification(CommandsSpecification.AT_LEAST_ONE);
        l_userCommand.pushCommandArgument(new CommandLineArgument(
                "add",
                2,
                ArgumentsSpecification.EQUAL
        ));
        l_userCommand.pushCommandArgument(new CommandLineArgument(
                "remove",
                2,
                ArgumentsSpecification.EQUAL
        ));
        l_userCommand.setGamePhaseMethodName("editNeighbor");
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > savemap filename
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("savemap");
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        l_userCommand.setGamePhaseMethodName("saveMap");
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > editmap filename
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("editmap");
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        l_userCommand.setGamePhaseMethodName("editMap");
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > validatemap
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("validatemap");
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE);
        l_userCommand.setGamePhaseMethodName("validateMap");
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
