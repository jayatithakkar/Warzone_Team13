package com.APP.Project.UserInterface.constants;

import com.APP.Project.Main;

import com.APP.Project.UserInterface.layouts.PlayerCommandLayout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for commands.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class CommandsTestCases {
    String d_headCommand;
    String d_argumentKey;
    String d_wrongArgumentKey;

    /**
     * Constructor for CommandsTestCases.
     */
    public CommandsTestCases() {

    }

    /**
     * Sets up initial conditions before each test method is executed.
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
     * Tests if the given key is a key of the command.
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
