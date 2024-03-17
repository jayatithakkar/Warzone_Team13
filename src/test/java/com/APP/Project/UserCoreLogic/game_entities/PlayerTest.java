package com.APP.Project.UserCoreLogic.game_entities;

import com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.UserCoreLogic;
import com.APP.Project.UserInterface.UserInterfaceClass;
import com.APP.Project.UserCoreLogic.GameEngine;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.phases.PlaySetup;
import com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertNotNull;

/**
 * Tests whether the player is able to issue the order correctly or not.
 *
 * @author Sushant Sinha
 */
public class PlayerTest {
    private static Main d_Application;
    private UserInterfaceClass d_commandLineInterface;
    private EditMapAdapter d_editMapService;
    private static URL d_TestFilePath;

    /**
     * Set the application environment.
     */
    @BeforeClass
    public static void beforeClass() {
        d_Application = new Main();
        d_Application.handleApplicationStartup();
        d_TestFilePath = PlayerTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }

    /**
     * Loads different objects and executes testcase.
     *
     * @throws AbsentTagException        Throws if any tag is missing in map file.
     * @throws InvalidMapException       Throws if map is invalid.
     * @throws ResourceNotFoundException Throws if map file not found.
     * @throws InvalidInputException     Throws if input command is invalid.
     * @throws EntityNotFoundException   Throws if entity not found.
     * @throws URISyntaxException        If error while parsing the string representing the path.
     * @see EditMapAdapter#handleLoadMap If any exception thrown.
     */
    @Before
    public void beforeTestCase() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException, URISyntaxException {
        // UserInterface to read and interpret the user input
        d_commandLineInterface = new UserInterfaceClass(d_Application);

        // (Re)initialise the UserCoreLogic.
        UserCoreLogic.getInstance().initialise();

        // EditMap service to load the map
        d_editMapService = new EditMapAdapter();
        assertNotNull(d_TestFilePath);
        String l_url = new URI(d_TestFilePath.getPath()).getPath();
        d_editMapService.handleLoadMap(l_url);

        // Set the game state to GAME_PLAY
        GameEngine.getInstance().setGamePhase(new PlaySetup(GameEngine.getInstance()));

        List<Country> l_assignedCountries = MapFeatureEngine.getInstance().getCountryList().subList(0, Math.min(4, MapFeatureEngine.getInstance().getCountryList().size()));
        Player l_player1 = new Player();
        l_player1.setName("User_1");
        l_player1.setAssignedCountries(l_assignedCountries);
        l_player1.setReinforcementCount(10);

        Player l_player2 = new Player();
        l_player2.setName("User_2");
        l_player2.setAssignedCountries(l_assignedCountries);
        l_player2.setReinforcementCount(10);

        GamePlayEngine.getInstance().addPlayer(l_player1);
        GamePlayEngine.getInstance().addPlayer(l_player2);
    }
}

