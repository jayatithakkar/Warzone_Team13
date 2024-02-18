package com.APP.Project.UserCoreLogic.common.services;

import com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.constants.interfaces.StandaloneCommand;

import java.util.List;

/**
 * Handling the request received from Main
 *
 * @author Sushant Sinha
 * @version 1.0
 */
public class ApplicationExitService implements StandaloneCommand {
    /**
     * {@inheritDoc}
     *
     * @param p_commandValues Represents the values passed while running the command.
     * @return Value of execution response of the user command.
     */
    @Override
    public String execute(List<String> p_commandValues) {
        Main.exit();
        return null;
    }
}
