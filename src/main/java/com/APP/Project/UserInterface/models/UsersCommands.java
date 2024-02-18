package com.APP.Project.UserInterface.models;

import com.APP.Project.UserInterface.constants.specifications.CommandsSpecification;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is used to understand and process the command entered by the user
 *
 * @author Jayati Thakkar
 * @version 1.0
 */

public class UsersCommands {

    /**
     * the header of the command
     */
    private String headCommand;

    /**
     * a map of the keys and its values for the arguments the user has to enter
     */
    private List<Map<String, List<String>>> d_userArguments;

    /**
     * the list of the command related values
     */
    private List<String> commandValuesList;

    /**
     * an object of the predefined user commands class
     */
    private PredefinedUserCommands d_predefinedUserCommand;

    /**
     * true if the command is <code> exit </code>
     */
    private boolean d_isExitCommand = false;

    /**
     * default constructor
     */

    public UsersCommands() {

    }

    /**
     * parameterized constructor
     *
     * @param p_predefinedUserCommand an object of the Predefined user commands class
     */
    public UsersCommands(PredefinedUserCommands p_predefinedUserCommand) {
        setHeadCommand(p_predefinedUserCommand.getHeadCommand());
        d_predefinedUserCommand = p_predefinedUserCommand;
        // Initialise references
        d_userArguments = new ArrayList<>();
        commandValuesList = new ArrayList<>();
    }


    /**
     * getter for header
     * @return the header of the command
     */

    public String getHeadCommand() {
        return headCommand;
    }

    /**
     * sets the header of the command into its variable
     * @param p_headCommand a string containing the header of the command
     */
    public void setHeadCommand(String p_headCommand) {
        headCommand = p_headCommand;
    }

    /**
     * it gets all the userrguments and returns the key and value of those lists
     * @return
     */
    @JsonIgnore
    public List<Map<String, List<String>>> getUserArguments() {
        return d_userArguments;
    }

    public void pushUserArgument(String argKey, List<String> values) {
        Map<String, List<String>> l_newArgumentKeyValue = new HashMap<>();
        l_newArgumentKeyValue.put(argKey, values);
        d_userArguments.add(l_newArgumentKeyValue);
    }

    /**
     * getter for the command related values
     * @return the list of string containing the values possible
     */
    public List<String> getCommandValuesList() {
        return commandValuesList;
    }

    /**
     * setter to set the command related values
     *
     * @param p_commandValuesList
     */
    public void setCommandValuesList(List<String> p_commandValuesList) {
        this.commandValuesList = p_commandValuesList;
    }

    /**
     * it gets predefined version of the users command.
     * @return the version of predefined form
     */
    @JsonIgnore
    public PredefinedUserCommands getPredefinedUserCommand() {
        return d_predefinedUserCommand;
    }

    /**
     * overridden equals method to check if it equal
     * @param l_p_o object that needs to be compared
     * @return true if it is equal; false otherwise
     */
    @Override
    public boolean equals(Object l_p_o) {
        if (this == l_p_o) return true;
        if (l_p_o == null || getClass() != l_p_o.getClass()) return false;
        UsersCommands that = (UsersCommands) l_p_o;
        return d_isExitCommand == that.d_isExitCommand && headCommand.equals(that.headCommand) && d_userArguments.equals(that.d_userArguments) && d_predefinedUserCommand.equals(that.d_predefinedUserCommand);
    }
}
