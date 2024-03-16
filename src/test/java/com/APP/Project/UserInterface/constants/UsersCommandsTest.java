package com.APP.Project.UserInterface.constants;

import com.APP.Project.UserInterface.layouts.CommandLayout;
import com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.common.services.ApplicationExitService;

import com.APP.Project.UserInterface.layouts.PlayerCommandLayout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of user commands within the application.
 * This class ensures that commands are properly recognized and processed,
 * focusing on the validity of command arguments.
 * 
 * @author Bhoomiben bhatt
 */
public class UsersCommandsTest {
    String d_headCommand;
    String d_argumentKey;
    String d_wrongArgumentKey;

    /**
     * Initializes a new instance of the {@link UsersCommandsTest} class.
     * This constructor is used to set up any required resources or initial states
     * before the tests are run.
     */
    public UsersCommandsTest() {

    }

    /**
     * Sets up the environment before each test method is executed.
     * Initializes command names and arguments to be used in test methods.
     */

    @Before
    public void Before() {
        this.d_headCommand = "editcontinent";
        this.d_argumentKey = "-add";
        this.d_wrongArgumentKey = "-delete";
        Main l_app = new Main();
        l_app.handleApplicationStartup();

    }

    /**
     * Verifies if the correct argument is recognized as part of the command.
     * Asserts that a valid argument is correctly identified.
     * Also checks if an invalid argument is rightly not recognized by the command.
     */
    @Test
    public void testIsKeyOfCommand() {
        boolean editContentKeyExists = PlayerCommandLayout.getUserCommand(this.d_headCommand)
                .isKeyOfCommand(this.d_argumentKey);
        boolean editContentKeyDoesnotExists = PlayerCommandLayout.getUserCommand(this.d_headCommand)
                .isKeyOfCommand(this.d_wrongArgumentKey);
        Assert.assertTrue(editContentKeyExists);
        Assert.assertFalse(editContentKeyDoesnotExists);
    }
}
