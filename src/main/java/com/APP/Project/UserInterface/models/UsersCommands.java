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

    private String d_headerCommand;

    private List<CommandLineArgument> d_argumentList;

    private Map<String, List<String>> d_userArguments;

    private CommandsSpecification d_commandSpecification;

    /**
     * default constructor
     */
    public UsersCommands() {
        d_argumentList = new ArrayList<CommandLineArgument>();
        d_userArguments = new HashMap<>();
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
     * getter for all the arguments
     * @return the list of arguments of the given command
     */

    public List<CommandLineArgument> getArgumentList() {
        return d_argumentList ;
    }

    /**
     * arguments are push inside of the list
     * @param p_commandArgument the list of argument
     */
    public void pushCommandArgument(CommandLineArgument p_commandArgument) {
        d_argumentList.add(p_commandArgument);
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

    public List<String> getArgumentKeys() {
        return this.d_argumentList .stream().map((CommandLineArgument::getD_argument))
                .collect(Collectors.toList());
    }

    public CommandLineArgument matchCommandArgument(String p_argumentKey) {
        // Returns only one element
        return this.d_argumentList .stream().filter((p_p_argumentKey) ->
                p_argumentKey.equals("-".concat(p_p_argumentKey.getD_argument()))
        ).collect(Collectors.toList()).get(0);
    }

    public boolean isKeyOfCommand(String p_argKey) {
        if (!p_argKey.startsWith("-"))
            return false;
        return this.getArgumentKeys().stream().anyMatch((p_p_argKey) ->
                p_argKey.equals("-".concat(p_p_argKey))
        );
    }

    public void setCommandSpecification(CommandsSpecification p_commandSpecification) {
        this.d_commandSpecification = p_commandSpecification;
    }


    public CommandsSpecification getCommandSpecification() {
        return d_commandSpecification;
    }

    @Override
    public boolean equals(Object l_p_o) {
        if (this == l_p_o) return true;
        if (l_p_o == null || getClass() != l_p_o.getClass()) return false;
        UsersCommands l_that = (UsersCommands) l_p_o;
        return Objects.equals(d_headerCommand, l_that.d_headerCommand) &&
                Objects.equals(d_userArguments.keySet(), l_that.d_userArguments.keySet());
    }


}
