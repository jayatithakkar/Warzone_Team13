package com.APP.Project.UserInterface.constants;

import com.APP.Project.Main;
import com.APP.Project.UserInterface.layouts.PlayerCommandLayout;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class contains unit tests for validating user commands.
 *
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
public class UsersCommandsTest {
    
    String d_headOfCommand;
    
    String d_argKey;
    
    String d_wrongArgKey;

    /**
     * Sets up the necessary data before each test case.
     */
    @Before
    public void before() {
        d_headOfCommand = "editcontinent";
        d_argKey = "-add";
        d_wrongArgKey = "-delete";
        Main l_application = new Main();
        l_application.handleApplicationStartup();
    }

    /**
     * Tests if the provided key matches the command.
     */
    @Test
    public void testIsKeyOfCommand() {
        boolean isKeyOfEditContent = PlayerCommandLayout.matchAndGetUserCommand(d_headOfCommand).isKeyOfCommand(this.d_argKey);

        boolean isNotKeyOfEditContent = PlayerCommandLayout.matchAndGetUserCommand(d_headOfCommand).isKeyOfCommand(this.d_wrongArgKey);

        assertTrue(isKeyOfEditContent);
        assertFalse(isNotKeyOfEditContent);
    }
}
