package com.APP.Project.UserInterface.layouts.commands;

import com.APP.Project.UserInterface.constants.specifications.ArgumentsSpecification;
import com.APP.Project.UserInterface.constants.specifications.CommandsSpecification;
import com.APP.Project.UserInterface.layouts.CommandLayout;
import com.APP.Project.UserInterface.models.CommandLineArgument;
import com.APP.Project.UserInterface.models.PredefinedUserCommands;

import java.util.ArrayList;
import java.util.List;

public class MapEditorCommand implements CommandLayout {

    List<PredefinedUserCommands> d_userCommands;

    private final List<PredefinedUserCommands> d_playerCommands;

    // Default constructor
    public MapEditorCommand() {
        d_playerCommands = new ArrayList<>();
        d_userCommands = new ArrayList<>();
        PredefinedUserCommands l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("editcontinent");
        l_userCommand.setCommandSpecification(CommandsSpecification.NEED_ONE);
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
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > editcountry -add countryID continentID -remove countryID
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("editcountry");
        l_userCommand.setCommandSpecification(CommandsSpecification.NEED_ONE);
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
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("editneighbor");
        l_userCommand.setCommandSpecification(CommandsSpecification.NEED_ONE);
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
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > showmap
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("showmap");
        l_userCommand.setCommandSpecification(CommandsSpecification.RUN_ALONE);
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > savemap filename
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("savemap");
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > editmap filename
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("editmap");
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > validatemap
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("validatemap");
        l_userCommand.setCommandSpecification(CommandsSpecification.RUN_ALONE);
        d_userCommands.add(l_userCommand);

        // Example of the below command:
        // > loadmap filename
        l_userCommand = new PredefinedUserCommands();
        l_userCommand.setHeadCommand("loadmap");
        l_userCommand.setCommandSpecification(CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE);
        d_userCommands.add(l_userCommand);
    }

    // Get and list predefined commands
    @Override
    public List<PredefinedUserCommands> fetchUserCommands() {
        return d_playerCommands;
    }
}
