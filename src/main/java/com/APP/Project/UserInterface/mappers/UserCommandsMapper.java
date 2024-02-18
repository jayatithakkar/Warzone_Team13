package com.APP.Project.UserInterface.mappers;

import java.util.*;
import java.util.stream.Collectors;

import com.APP.Project.UserInterface.constants.specifications.ArgumentsSpecification;
import com.APP.Project.UserInterface.exceptions.InvalidArgumentException;
import com.APP.Project.UserInterface.exceptions.InvalidCommandException;
import com.APP.Project.UserInterface.layouts.PlayerCommandLayout;
import com.APP.Project.UserInterface.models.PredefinedUserCommands;
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
        
        l_commands = l_commands.stream().filter(l_command ->
                !l_command.trim().isEmpty()
        ).collect(Collectors.toList());
        
        if (l_commands.size() > 0) {
            String l_headOfCommand = l_commands.get(0);
//            System.out.println("working and command is "+l_headOfCommand);
            PredefinedUserCommands l_predefinedUserCommand =
                    PlayerCommandLayout.getUserCommand(l_headOfCommand);
                    
            UsersCommands l_parsedUserCommand = new UsersCommands(l_predefinedUserCommand);

            List<String> l_argumentBody = l_commands.subList(1, l_commands.size());

            // Throws an exception if the command needs its value to run and not provided by the user
            if (validateIfCommandDoesNeedValue(l_predefinedUserCommand, l_argumentBody.size())) {
                // This UserCommand instance will be returned from here.
                l_parsedUserCommand.setCommandValuesList(l_argumentBody);
                return l_parsedUserCommand;
            } else if (validateIfCommandCanRunAlone(l_predefinedUserCommand, l_argumentBody.size())) {
                // Throws an exception if the command can run alone and the user has provided with some random text
            } else {
                // Throws an exception if the command does not have any argument
                // Need to be checked again after extracting the argument and validating it.
                validateIfCommandDoesNeedArgument(l_predefinedUserCommand, l_argumentBody.size());
            }


            // If the command can accept arguments and user have provided the arguments
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


                // Throws an exception if the input does not have any argument
                validateIfCommandDoesNeedArgument(l_predefinedUserCommand, l_positionOfArgKeyList.size());

                // If user has provided the argument(s)
                for (int l_currentPosIndex = 0; l_currentPosIndex < l_positionOfArgKeyList.size(); l_currentPosIndex++) {
                    // The index of the argument key and the next argument key in the found-position list
                    int l_nextPosIndex = l_currentPosIndex + 1;

                    // If there is next key, find the index of that key
                    int l_indexOfNextKey;
                    if (l_nextPosIndex == l_positionOfArgKeyList.size()) {
                        l_indexOfNextKey = l_argumentBody.size();
                    } else {
                        l_indexOfNextKey = l_positionOfArgKeyList.get(l_nextPosIndex);
                    }

                    // Get argument details for the current argument key
                    String l_currentArgKey = l_argumentBody.get(l_positionOfArgKeyList.get(l_currentPosIndex));
                    CommandLineArgument l_commandArgument = l_predefinedUserCommand.matchCommandArgument(l_currentArgKey);
                    int l_indexOfCurrentKey = l_positionOfArgKeyList.get(l_currentPosIndex);

                    // Get the number of values provided by the user for the current argument
                    List<String> l_values = l_argumentBody.subList(l_indexOfCurrentKey + 1, l_indexOfNextKey);

                    // Checks if the user has entered the correct number of values for the argument
                    if (l_commandArgument.getD_specification() == ArgumentsSpecification.EQUAL &&
                            l_commandArgument.getD_values() == l_values.size()) {
                        l_parsedUserCommand.pushUserArgument(l_commandArgument.getD_argument(), l_values);
                        continue;
                    } else if (l_commandArgument.getD_specification() == ArgumentsSpecification.MAX &&
                            l_commandArgument.getD_values() >= l_values.size()) {
                        l_parsedUserCommand.pushUserArgument(l_commandArgument.getD_argument(), l_values);
                        continue;
                    } else if (l_commandArgument.getD_specification() == ArgumentsSpecification.MIN &&
                            l_commandArgument.getD_values() <= l_values.size()) {
                        l_parsedUserCommand.pushUserArgument(l_commandArgument.getD_argument(), l_values);
                        continue;
                    }


                    // Throw if the user has not provided the correct number of values
                    if (l_commandArgument.getD_specification() != ArgumentsSpecification.MAX &&
                            l_commandArgument.getD_values() >= l_values.size()
                    ) {
                        throw new InvalidArgumentException("Required argument values not provided!");
                    } else {
                        throw new InvalidArgumentException("Unrecognised argument values");
                    }
                }
            }
            return l_parsedUserCommand;
        } else {
            // If user has entered only spaces
            throw new InvalidCommandException("Invalid user input!");
        }
    }


    /**
     * Checks if the command doesn't need any argument to run
     *
     * @param p_predefinedUserCommand Value of the command which has the specification
     * @param p_numOfKeys             Value of the number of keys entered by the user
     * @return True if the command can run alone; false otherwise
     */
    private boolean validateIfCommandCanRunAlone(PredefinedUserCommands p_predefinedUserCommand, int p_numOfKeys) {
        if (p_predefinedUserCommand.getCommandSpecification() == CommandsSpecification.RUN_ALONE) {
            // Means the user has entered the not-needful text after the command
            if (p_numOfKeys > 0)
                throw new InvalidArgumentException("Unrecognized argument!");
            return true;
        }
        return false;
    }


    /**
     * Checks if the command needs value to proceed
     *
     * @param p_predefinedUserCommand Value of the command which has the specification.
     * @param p_numOfKeys             Value of the number of text entered by the user after the command.
     * @return True if the command can run alone; false otherwise.
     */
    private boolean validateIfCommandDoesNeedValue(PredefinedUserCommands p_predefinedUserCommand, int p_numOfKeys) {
        if (p_predefinedUserCommand.getCommandSpecification() == CommandsSpecification.CAN_RUN_ALONE_WITH_VALUE) {
            // Means the user has not provided the value required with the command
            if (p_numOfKeys == 0)
                throw new InvalidArgumentException("Value not provided");
            return true;
        }
        return false;
    }

    /**
     * Checks if the argument body is empty and the command needs an argument to run
     *
     * @param p_predefinedUserCommand Value of the command which has the specification
     * @param p_numOfKeys             Value of the number of keys entered by the user
     * @return True if the entered command has at least one argument; false otherwise
     */
    private boolean validateIfCommandDoesNeedArgument(PredefinedUserCommands p_predefinedUserCommand, int p_numOfKeys) {
        if (p_predefinedUserCommand.getCommandSpecification() == CommandsSpecification.AT_LEAST_ONE) {
            // Means the user has not provided any required argument keys
            if (p_numOfKeys == 0)
                throw new InvalidArgumentException("Command requires at least one argument to run!");
            return true;
        }
        return false;
    }
}