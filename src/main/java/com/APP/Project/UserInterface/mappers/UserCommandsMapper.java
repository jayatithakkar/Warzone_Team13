package com.APP.Project.UserInterface.mappers;

import java.util.*;

public class UserCommandsMapper {

    public UserCommand toUserCommand(String p_userInput) {
        // Cracks a command line string
        List<String> l_commands = Arrays.asList(p_userInput.split("\\s"));

        if (l_commands.size() > 0) {
            // UserCommand will be converted as user has entered
            UserCommand l_userCommand = new UserCommand();
            String l_headOfCommand = l_commands.get(0);
            l_userCommand.setHeadCommand(l_headOfCommand);
            // Fetched predefined structure of the user command using the head
            UserCommand l_predefinedUserCommand =
                    PlayerCommandLayout.getUserCommand(l_headOfCommand);

            // Represents the body of the command line
            List<String> l_argumentBody = l_commands.subList(1, l_commands.size());

            // Throws an exception if the command can not run alone
            canCommandRunAlone(l_predefinedUserCommand, l_argumentBody.size());

            // Throws an exception if the command does not have any argument
            // Need to be checked again after extracting the argument and validating it.
            doesCommandNeedArgument(l_predefinedUserCommand, l_argumentBody.size());

            // If the command can accept arguments and user have provided the arguments
            if (l_predefinedUserCommand.getCommandArgumentList().size() > 0 &&
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
                doesCommandNeedArgument(l_predefinedUserCommand, l_positionOfArgKeyList.size());

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
                    CommandArgument l_commandArgument = l_predefinedUserCommand.matchCommandArgument(l_currentArgKey);
                    int l_indexOfCurrentKey = l_positionOfArgKeyList.get(l_currentPosIndex);

                    // Get the number of values provided by the user for the current argument
                    List<String> l_values = l_argumentBody.subList(l_indexOfCurrentKey + 1, l_indexOfNextKey);

                    // Checks if the user has entered the correct number of values for the argument
                    if (l_commandArgument.getSpecification() == ArgumentSpecification.EQUAL &&
                            l_commandArgument.getNumOfValues() == l_values.size()) {
                        l_userCommand.pushUserArgument(l_commandArgument.getArgumentKey(), l_values);
                        continue;
                    } else if (l_commandArgument.getSpecification() == ArgumentSpecification.MAX &&
                            l_commandArgument.getNumOfValues() >= l_values.size()) {
                        l_userCommand.pushUserArgument(l_commandArgument.getArgumentKey(), l_values);
                        continue;
                    } else if (l_commandArgument.getSpecification() == ArgumentSpecification.MIN &&
                            l_commandArgument.getNumOfValues() <= l_values.size()) {
                        l_userCommand.pushUserArgument(l_commandArgument.getArgumentKey(), l_values);
                        continue;
                    }

                    // Throw if the user has not provided the correct number of values
                    if (l_commandArgument.getSpecification() != ArgumentSpecification.MAX &&
                            l_commandArgument.getNumOfValues() >= l_values.size()
                    ) {
                        throw new InvalidArgumentException("Required argument values not provided!");
                    } else {
                        throw new InvalidArgumentException("Unrecognised argument values");
                    }
                }
            }
            return l_userCommand;
        } else {
            // If user has entered only spaces
            throw new InvalidCommandException("Invalid user input!");
        }
    }


    private boolean canCommandRunAlone(UserCommand p_userCommand, int numOfKeys) {
        if (p_userCommand.getCommandSpecification() == CommandSpecification.CAN_RUN_ALONE &&
                numOfKeys > 0) {
            throw new InvalidArgumentException("Unrecognized argument!");
        }
        return true;
    }

    private boolean doesCommandNeedArgument(UserCommand p_userCommand, int numOfKeys) {
        if (p_userCommand.getCommandSpecification() == CommandSpecification.AT_LEAST_ONE &&
                numOfKeys == 0) {
            throw new InvalidArgumentException("Command requires at least one argument to run!");
        }
        return true;
    }

}
