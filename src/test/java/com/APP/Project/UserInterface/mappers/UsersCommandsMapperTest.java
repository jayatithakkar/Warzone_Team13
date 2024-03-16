package com.APP.Project.UserInterface.mappers;

import com.APP.Project.Main;
import com.APP.Project.UserInterface.layouts.PlayerCommandLayout;
import com.APP.Project.UserInterface.models.UsersCommands;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality for mapping user input text to the corresponding
 * {@link UsersCommands} objects.
 * This class validates that the user input, whether it includes command
 * arguments or values, is correctly interpreted and
 * transformed into a structured command representation.
 * 
 * @author Bhoomiben Bhatt
 */
public class UsersCommandsMapperTest {
    /**
     * Example user input text with a command and its arguments.
     */
    private String d_commandWithArgument;
    private String d_commandWithValue;

    /**
     * The expected {@link UsersCommands} object corresponding to
     * {@link #d_commandWithArgument}.
     * The expected {@link UsersCommands} object corresponding to
     * {@link #d_commandWithValue}.
     */
    private UsersCommands d_correctCommandWithArgument;
    private UsersCommands d_correctCommandWithValue;

    /**
     * Initializes the application context once before any of the test methods in
     * the class.
     * This method sets up the necessary environment for the tests, ensuring the
     * application is started.
     */
    @BeforeClass
    public static void beforeClass() {
        Main l_application = new Main();
        l_application.handleApplicationStartup();
    }

    /**
     * Prepares the test environment before each test, setting up the necessary
     * command examples and their expected interpretations.
     * This includes initializing the map feature engine and configuring the
     * expected {@link UsersCommands} objects based on predefined user input.
     */
    @Before
    public void before() {
        MapFeatureEngine.getInstance().initialise();
        d_commandWithArgument = "editcontinent -add Canada 10 -remove Continent";
        d_correctCommandWithArgument = new UsersCommands(PlayerCommandLayout.matchAndGetUserCommand("editcontinent"));
        d_correctCommandWithArgument.pushUserArgument("add",
                Arrays.asList("continentID", "continentvalue"));
        d_correctCommandWithArgument.pushUserArgument("remove",
                Collections.singletonList("continentID"));

        d_commandWithValue = "savemap filename";
        d_correctCommandWithValue = new UsersCommands(PlayerCommandLayout.matchAndGetUserCommand("savemap"));
        d_correctCommandWithValue.setCommandValues(Collections.singletonList("filename"));

        // Sets the game state to MAP_EDITOR
        Main l_application = new Main();
        l_application.handleApplicationStartup();
    }

    /**
     * Tests the mapping of user input text to the corresponding
     * {@link UsersCommands} objects.
     * This test verifies that the user input is accurately interpreted and matches
     * the expected command structure.
     */
    @Test
    public void testUserInput() {
        // Mapper class which maps text to interpreted command.
        UserCommandsMapper l_userCommandMapper = new UserCommandsMapper();

        // Interprets the user text
        UsersCommands l_commandWithArgument = l_userCommandMapper.toUserCommand(this.d_commandWithArgument);
        UsersCommands l_commandWithValue = l_userCommandMapper.toUserCommand(this.d_commandWithValue);

        assertEquals(l_commandWithArgument, d_correctCommandWithArgument);
        assertEquals(l_commandWithValue, d_correctCommandWithValue);
    }
}
