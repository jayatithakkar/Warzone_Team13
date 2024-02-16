package com.APP.Project.UserInterface.models;

import com.APP.Project.UserInterface.constants.specifications.CommandsSpecification;

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
     * the headeer of the command
     */
    private String d_headerCommand;

    /**
     * a map of the keys and its values for the arguments the user has to enter
     */
    private Map<String, List<String>> d_userArguments;

    /**
     * the list of the command related values
     */
    private List<String> d_CommandRelatedValues;

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
        setHeaderCommand(p_predefinedUserCommand.getHeaderCommand());
        d_predefinedUserCommand = p_predefinedUserCommand;
        // Initialise references
        d_userArguments = (Map<String, List<String>>) new ArrayList<>();
        d_CommandRelatedValues = new ArrayList<>();
    }


    /**
     * getter for header
     * @return the header of the command
     */

    public String getHeaderCommand() {
        return d_headerCommand;
    }

    /**
     * sets the header of the command into its variable
     * @param p_headerCommand a string containing the header of the command
     */
    public void setHeaderCommand(String p_headerCommand) {
        d_headerCommand = p_headerCommand;
    }

    /**
     * it gets all the userrguments and returns the key and value of those lists
     * @return
     */
    public Map<String, List<String>> getUserArguments() {
        return d_userArguments;
    }

    public void pushUserArgument(String argKey, List<String> values) {
        d_userArguments.put(argKey, values);
    }

    /**
     * getter for the command related values
     * @return the list of string containing the values possible
     */
    public List<String> getD_CommandRelatedValues() {
        return d_CommandRelatedValues;
    }

    /**
     * setter to set the command related values
     *
     * @param d_commandRelatedValues
     */
    public void setCommandRelatedValues(List<String> d_commandRelatedValues) {
        this.d_CommandRelatedValues = d_commandRelatedValues;
    }

    /**
     * it gets predefined version of the users command.
     * @return the version of predefined form
     */
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
        UsersCommands l_that = (UsersCommands) l_p_o;
        return Objects.equals(d_headerCommand, l_that.d_headerCommand) &&
                Objects.equals(d_userArguments.keySet(), l_that.d_userArguments.keySet());
    }


}
