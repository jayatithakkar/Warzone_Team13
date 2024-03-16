package com.APP.Project.UserCoreLogic.responses;

import java.util.List;

/**
 * Encapsulates the details of a user command issued during the
 * {@code GAME_STATE#GAME_PLAY} phase of the game.
 * <p>
 * This class serves as a container for command information interpreted from
 * user interactions via the {@code UserInterface}.
 * It breaks down a user command into its primary component (the head of the
 * command) and any associated values, facilitating
 * further processing or execution of game logic based on the user's input.
 * Additionally, it provides utility methods to
 * determine specific command states, such as whether a player has completed
 * issuing commands.
 * </p>
 * 
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
public class CommandResponses {
    /**
     * The primary identifier or keyword of the command, representing the action to
     * be taken.
     */
    private String d_headCommand;

    /**
     * A list of values associated with the command, representing parameters or
     * arguments to the action specified by {@code d_headCommand}.
     */
    private List<String> d_commandValues;

    /**
     * Retrieves the primary identifier or action keyword of the user's command.
     *
     * @return The head of the command, indicating the intended action.
     */
    public String getHeadCommand() {
        return d_headCommand;
    }

    /**
     * Sets the primary identifier or action keyword for the user's command.
     *
     * @param p_headCommand The head of the command to be set.
     */
    public void setHeadCommand(String p_headCommand) {
        d_headCommand = p_headCommand;
    }

    /**
     * Retrieves the list of values or parameters associated with the command.
     *
     * @return A list of strings representing the values or arguments for the
     *         command.
     */
    public List<String> getCommandValues() {
        return d_commandValues;
    }

    /**
     * Sets the list of values or parameters associated with the command.
     *
     * @param d_commandValues The list of strings to be set as values or arguments
     *                        for the command.
     */
    public void setCommandValues(List<String> d_commandValues) {
        this.d_commandValues = d_commandValues;
    }

    /**
     * Determines whether the user command indicates a request to stop issuing
     * further orders.
     *
     * This utility method checks if the head of the command equals "done",
     * signaling that the user wishes to
     * end their command input session.
     *
     * @return {@code true} if the user command is "done", indicating no further
     *         orders will be issued; otherwise {@code false}.
     */
    public boolean isDone() {
        return getHeadCommand().equals("done");
    }
}
