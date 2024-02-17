package com.APP.Project.UserInterface.models;

import com.APP.Project.UserInterface.constants.specifications.CommandsSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * this provides a structure for predefined commands the user is supposed to enter
 *
 * @author Jayati Thakkar
 * @version 1.0
 */

public class PredefinedUserCommands {

    private String d_headerCommand;

    private final List<CommandLineArgument> d_commandArgumentList;

    private CommandsSpecification d_commandSpecification;

    private boolean d_isGameEngineCommand = false;

    private boolean d_isGameEngineStartCommand = false;

    /**
     * It initializes all the members.
     */

    public PredefinedUserCommands() {
        // Initialise references
        d_commandArgumentList = new ArrayList<>();
    }

    /**
     * gives the header of the command
     * @return returns the header of the command
     */
    public String getHeaderCommand() {
        return d_headerCommand;
    }

    /**
     * sets the heafer command
     * @param p_headerCommand string value that will initialize the header variable
     */
    public void setHeadCommand(String p_headerCommand) {
        d_headerCommand = p_headerCommand;
    }

    /**
     * add elements to the list of commandLineArgument object.
     * @param p_commandArgument An object of the same class
     */

    public void pushCommandArgument(CommandLineArgument p_commandArgument) {
        d_commandArgumentList.add(p_commandArgument);
    }

    /**
     * returns the list of rguments
     * @return the list of the keys of the arguments
     */
    public List<String> getArgumentKeys() {
        return this.d_commandArgumentList.stream().map((CommandLineArgument::getD_argument))
                .collect(Collectors.toList());
    }

    /**
     * it tries matching the string variable with the list available for the possible arguments for the given command
     *
     * @param p_argumentKey the variable that needs the command to be checked
     * @return alue of available list
     */
    public CommandLineArgument matchCommandArgument(String p_argumentKey) {
        // Returns only one element
        return this.d_commandArgumentList.stream().filter((p_p_argumentKey) ->
                p_argumentKey.equals("-".concat(p_p_argumentKey.getD_argument()))
        ).collect(Collectors.toList()).get(0);
    }

    /**
     * checks if it is the key for the command
     *
     * @param p_argKey the string that needs to be checked
     * @return it returns true if it martches; false otherwise
     */
    public boolean isKeyOfCommand(String p_argKey) {
        if (!p_argKey.startsWith("-"))
            return false;
        return this.getArgumentKeys().stream().anyMatch((p_p_argKey) ->
                p_argKey.equals("-".concat(p_p_argKey))
        );
    }

    /**
     * setter
     * @param p_commandSpecification CommandSpecification object
     */
    public void setCommandSpecification(CommandsSpecification p_commandSpecification) {
        this.d_commandSpecification = p_commandSpecification;
    }

    /**
     * getter
     * @return returns the CommandSpecification object
     */
    public CommandsSpecification getCommandSpecification() {
        return d_commandSpecification;
    }

    /**
     * checks if it is a command
     * @return true if is it a command; false otherwise
     */
    public boolean isGameEngineCommand() {
        return d_isGameEngineCommand;
    }

    /**
     * setter only if the engine can ask for the user input
     * @param p_gameEngineCommand true if the user can be asked; false otherwise
     */
    public void setGameEngineCommand(boolean p_gameEngineCommand) {
        d_isGameEngineCommand = p_gameEngineCommand;
    }

    /**
     * user input command to start the game
     *
     * @return true if it is a start command; false otherwise
     */
    public boolean isGameEngineStartCommand() {
        return d_isGameEngineStartCommand;
    }

    /**
     * it sets the start command for the game engine
     * @param p_gameEngineStartCommand true if the game is supposed to start; false otherwise
     */
    public void setGameEngineStartCommand(boolean p_gameEngineStartCommand) {
        d_isGameEngineStartCommand = p_gameEngineStartCommand;
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
        PredefinedUserCommands l_that = (PredefinedUserCommands) l_p_o;
        return Objects.equals(d_headerCommand, l_that.d_headerCommand) &&
                Objects.equals(d_commandArgumentList, l_that.d_commandArgumentList);
    }


}