package com.APP.Project.UserInterface.mappers;

import java.util.*;

import com.APP.Project.UserInterface.constants.specifications.ArgumentsSpecification;
import com.APP.Project.UserInterface.exceptions.InvalidArgumentException;
import com.APP.Project.UserInterface.exceptions.InvalidCommandException;
import com.APP.Project.UserInterface.layouts.PlayerCommandLayout;
import com.APP.Project.UserInterface.models.UsersCommands;
import com.APP.Project.UserInterface.models.CommandLineArgument;
import com.APP.Project.UserInterface.constants.specifications.CommandsSpecification;

/**
 * This class converts manual user command to its structured class object
 *
 * @author Jayati Thakkar
 * @version 1.0
 */
public class UserCommandsMapper {

    public UsersCommands toUserCommand(String p_userInput) {
        // user string is split using spaces
        List<String> l_commands = Arrays.asList(p_userInput.split("\\s"));

        if (l_commands.size() > 0) {
            UsersCommands l_userCommand = new UsersCommands();
            String l_headOfCommand = l_commands.get(0);
            l_userCommand.setHeaderCommand(l_headOfCommand);
            // predefined structure of the user command
            UsersCommands l_predefinedUserCommand = PlayerCommandLayout.getUserCommand(l_headOfCommand);


            List<String> l_argumentBody = l_commands.subList(1, l_commands.size());

            // exception is thrown if the command can not run alone
            canCommandRunAlone(l_predefinedUserCommand, l_argumentBody.size());

            doesCommandNeedArgument(l_predefinedUserCommand, l_argumentBody.size());

            // If the command can accept arguments and user have provided those arguments
            if (l_predefinedUserCommand.getArgumentKeys().size() > 0 &&
                    l_argumentBody.size() > 0) {
                // Stores the position of the argument keys in the argument body
                List<Integer> l_positionOfArgKeyList = new ArrayList<>();

                // Index, to keep track of the iteration over the l_argumentBody
                int l_index = 0;
                for (String l_command : l_argumentBody) {
                    if (l_predefinedUserCommand.isKeyOfCommand(l_command)) {
                        l_positionOfArgKeyList.add(l_index);
                    }
                    l_index++;
                }

                // if there are no arguments entered, exception is thrown
                doesCommandNeedArgument(l_predefinedUserCommand, l_positionOfArgKeyList.size());

                // only if user has entered arguments
                for (int l_currentPosIndex = 0; l_currentPosIndex < l_positionOfArgKeyList.size(); l_currentPosIndex++) {
                    // The index of the argument key and the next argument key in the found-position list
                    int l_nextPosIndex = l_currentPosIndex + 1;

                    // if next key exists, finds its index
                    int l_indexOfNextKey;
                    if (l_nextPosIndex == l_positionOfArgKeyList.size()) {
                        l_indexOfNextKey = l_argumentBody.size();
                    } else {
                        l_indexOfNextKey = l_positionOfArgKeyList.get(l_nextPosIndex);
                    }

                    // arguments for the current argument key
                    String l_currentArgKey = l_argumentBody.get(l_positionOfArgKeyList.get(l_currentPosIndex));
                    CommandLineArgument l_commandArgument = l_predefinedUserCommand.matchCommandArgument(l_currentArgKey);
                    int l_indexOfCurrentKey = l_positionOfArgKeyList.get(l_currentPosIndex);

                    // Get the number of values provided by the user for the current argument
                    List<String> l_values = l_argumentBody.subList(l_indexOfCurrentKey + 1, l_indexOfNextKey);

                    // if the number of values entered by the user is true or not
                    if (l_commandArgument.getD_specification() == ArgumentsSpecification.EQUAL &&
                            l_commandArgument.getD_values() == l_values.size()) {
                        l_userCommand.pushUserArgument(l_commandArgument.getD_argument(), l_values);
                        continue;
                    } else if (l_commandArgument.getD_specification() == ArgumentsSpecification.MAX &&
                            l_commandArgument.getD_values() >= l_values.size()) {
                        l_userCommand.pushUserArgument(l_commandArgument.getD_argument(), l_values);
                        continue;
                    } else if (l_commandArgument.getD_specification() == ArgumentsSpecification.MIN &&
                            l_commandArgument.getD_values() <= l_values.size()) {
                        l_userCommand.pushUserArgument(l_commandArgument.getD_argument(), l_values);
                        continue;
                    }

                    // if correct values are not entered, the exception is thrown
                    if (l_commandArgument.getD_specification() != ArgumentsSpecification.MAX &&
                            l_commandArgument.getD_values() >= l_values.size()
                    ) {
                        throw new InvalidArgumentException("Required argument values not provided!");
                    } else {
                        throw new InvalidArgumentException("Unrecognised argument values");
                    }
                }
            }
            return l_userCommand;
        } else {
            // If user enters only spaces
            throw new InvalidCommandException("Invalid user input!");
        }
    }

    /**
     * checks if the command does not need any argument and that it may run alone
     *
     * @param p_userCommand  commmand we need to check
     * @param numOfKeys      number of keys entered by the user
     * @return               returns true if it can run alone; false otherwise
     */

    private boolean canCommandRunAlone(UsersCommands p_userCommand, int numOfKeys) {
        if (p_userCommand.getCommandSpecification() == CommandsSpecification.RUN_ALONE &&
                numOfKeys > 0) {
            throw new InvalidArgumentException("Unrecognized argument!");
        }
        return true;
    }

    /**
     * checks if the command needs any argument or not
     *
     * @param p_userCommand  commmand we need to check
     * @param numOfKeys      number of keys entered by the user
     * @return               returns true if it needs any argument; false otherwise
     */

    private boolean doesCommandNeedArgument(UsersCommands p_userCommand, int numOfKeys) {
        if (p_userCommand.getCommandSpecification() == CommandsSpecification.NEED_ONE &&
                numOfKeys == 0) {
            throw new InvalidArgumentException("Command requires at least one argument to run!");
        }
        return true;
    }

}
