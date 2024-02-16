package com.APP.Project.UserCoreLogic.responses;

import java.util.List;

/**
 * Receives and decodes the commands from the UserInterface
 *
 * @author Sushant Sinha
 *
 */
public class CommandResponses {
    /**
     * Command received from the User
     */
    private String d_headCommands;

    /**
     * List to store the command values
     */
    private List<String> d_commandValuesList;

    /**
     * Receive the head of the command from the User
     *
     * @return head of the command
     */
    public String getHeadCommand() {
        return d_headCommands;
    }

    /**
     * Set the head command from the User's command
     *
     * @param p_headCommands head of the command
     */
    public void setHeadCommand(String p_headCommands) {
        d_headCommands = p_headCommands;
    }

    /**
     * Get the heads of the command
     *
     * @return List of values of the head
     */
    public List<String> getCommandValuesList() {
        return d_commandValuesList;
    }

    /**
     * Set values for the command using the provided list
     *
     * @param p_commandValuesList list values for command
     */
    public void setCommandValuesList(List<String> p_commandValuesList) {
        this.d_commandValuesList = p_commandValuesList;
    }
}
