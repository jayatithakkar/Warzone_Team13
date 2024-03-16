package com.APP.Project.UserCoreLogic.game_entities;

import com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;
import com.APP.Project.UserInterface.UserInterfaceClass;
import com.APP.Project.UserCoreLogic.UserCoreLogic;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Tests if the player can issue orders correctly.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class PlayerTest {
    private UserInterfaceClass d_userInterfaceClass;
    private static URL d_TestFilePath;

    /**
     * Set up the application environment before running test cases.
     */
    @BeforeClass
    public static void beforeClass() {
        Main d_Main = new Main();
        d_Main.handleApplicationStartup();
        d_TestFilePath = PlayerTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }

    /**
     * Loads different objects and performs necessary operations required to execute testcase.
     *
     * @throws AbsentTagException        Throws if any tag is missing in map file.
     * @throws InvalidMapException       Throws if map is invalid.
     * @throws ResourceNotFoundException Throws if map file not found.
     * @throws InvalidInputException     Throws if input command is invalid.
     * @throws EntityNotFoundException   Throws if entity not found.
     * @see EditMapAdapter#handleLoadMap If any exception thrown.
     */
    @Before
    public void beforeTestCase() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
        // UserInterface to read and interpret the user input
        d_userInterfaceClass = new UserInterfaceClass();

        // (Re)initialise the UserCoreLogic.
        UserCoreLogic.getInstance().initialise();

        // EditMap service to load the map
        EditMapAdapter d_editMapAdapter = new EditMapAdapter();
        d_editMapAdapter.handleLoadMap(d_TestFilePath.getPath());

        // Set the game state to GAME_PLAY
        UserCoreLogic.getInstance().setGameStatePlaying();

        List<Country> l_assignedCountries = MapFeatureEngine.getInstance().getAllCountryList().subList(0, Math.min(4, MapFeatureEngine.getInstance().getAllCountryList().size()));
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

    /**
     * Tests the player issue order functionality. An order is tested against the user input and it will be stored in
     * the player's order list.
     *
     * @throws UserCoreLogicException          Throws if any exception while processing the issue order request.
     * @throws ExecutionException   Throws if error occurs in execution.
     * @throws InterruptedException Throws if interruption occurs during normal execution.
     */
    @Test
    public void testIssueOrder() throws UserCoreLogicException, ExecutionException, InterruptedException {
        // User input text.
        String l_orderInput = "deploy Mercury-South 5";

        d_userInterfaceClass.setIn(new ByteArrayInputStream(l_orderInput.getBytes()));
        GamePlayEngine.getInstance().getPlayerList().get(0).issueOrder();
    }

    /**
     * Tests the player issue order functionality when the player enters more reinforcements to deploy than possessing.
     *
     * @throws UserCoreLogicException If any exception while processing the issue order request.
     * @throws ExecutionException   Throws if error occurs in execution.
     * @throws InterruptedException Throws if interruption occurs during normal execution.
     */
    @Test(expected = ReinforcementOutOfBoundException.class)
    public void testOutOfReinforcementIssueOrder() throws UserCoreLogicException, ExecutionException, InterruptedException {
        // User input text.
        String d_outOfResourcesOrderInput = "deploy Mercury-South 14";

        d_userInterfaceClass.setIn(new ByteArrayInputStream(d_outOfResourcesOrderInput.getBytes()));

        // Below will throw <code>ReinforcementOutOfBoundException</code> exception.
        GamePlayEngine.getInstance().getPlayerList().get(0).issueOrder();
    }
}

