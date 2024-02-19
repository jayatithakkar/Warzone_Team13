package com.APP.Project.UserInterface.services;

import com.APP.Project.Main;

import com.APP.Project.UserInterface.layouts.PlayerClassLayout;
import com.APP.Project.UserInterface.layouts.PlayerCommandLayout;
import com.APP.Project.UserInterface.models.UsersCommands;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserInterface.service.RequestsService;
import java.util.Arrays;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class contains test cases for the RequestsService class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class RequestsServiceTest {
    private UsersCommands d_usersCommands;

    /**
     * Default constructor for RequestsServiceTest class.
     */
    public RequestsServiceTest() {
    }

    /**
     * Executes before any test methods in the class. It initializes the application for testing.
     */
    @BeforeClass
    public static void beforeClass() {
        Main l_app = new Main();
        l_app.handleApplicationStartup();
    }

    /**
     * Executes before each test method. Initializes the map features and sets up user commands for testing.
     */
    @Before
    public void before() {
        MapFeatureEngine.getInstance().initialise();
        this.d_usersCommands = new UsersCommands(PlayerCommandLayout.getUserCommand("editcontinent"));
        this.d_usersCommands.pushUserArgument("add", Arrays.asList("continentID", "12"));
        Main l_app = new Main();
        l_app.handleApplicationStartup();
    }

    /**
     * Test case for the takeAction method in RequestsService class.
     * It verifies the method execution without throwing any exceptions.
     */
    @Test(expected = Test.None.class)
    public void testTakeAction() {
        RequestsService l_reqService = new RequestsService();
        l_reqService.takeAction(this.d_usersCommands);
    }
}
