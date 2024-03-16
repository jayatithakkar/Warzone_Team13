package com.APP.Project.UserCoreLogic.game_entities.orders;

import com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.UserCoreLogic;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;
import com.APP.Project.UserCoreLogic.gamePlay.services.DistributeCountriesService;
import com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class is for testing the steps performed when the "Deploy" command is executed.
 * Uses <code>DeployOrder</code>.
 *
 * @author Sushant Sinha
 */
public class DeployOrderTest {
    private static Main d_Application;
    private static URL d_TestFilePath;
    private static GamePlayEngine d_GamePlayEngine;
    private DistributeCountriesService d_distributeCountriesService;
    private List<Player> d_playerList;
    private Player d_player1;
    private Player d_player2;

    /**
     * Runs before the test case class runs.
     * Initializes different objects needed for performing the test.
     */
    @BeforeClass
    public static void beforeClass() {
        d_Application = new Main();
        d_Application.handleApplicationStartup();
        d_GamePlayEngine = GamePlayEngine.getInstance();
        d_TestFilePath = DeployOrderTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }

    /**
     * Setting up the objects needed for testing. Done before performing the test.
     *
     * @throws AbsentTagException        Throws if any tag is missing in the map file.
     * @throws InvalidMapException       Throws if map file is invalid.
     * @throws ResourceNotFoundException Throws if the file not found.
     * @throws InvalidInputException     Throws if user input is invalid.
     * @throws EntityNotFoundException   Throws if entity not found.
     * @throws URISyntaxException        Throws if URI syntax problem.
     */
    @Before
    public void before() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException, URISyntaxException {
        // (Re)initialise the UserCoreLogic.
        UserCoreLogic.getInstance().initialise();

        // Loads the map
        EditMapAdapter l_editMapService = new EditMapAdapter();
        assertNotNull(d_TestFilePath);
        String l_url = new URI(d_TestFilePath.getPath()).getPath();
        l_editMapService.handleLoadMap(l_url);

        Player l_player1 = new Player();
        Player l_player2 = new Player();

        l_player1.setName("User_1");
        l_player2.setName("User_2");

        d_GamePlayEngine.addPlayer(l_player1);
        d_GamePlayEngine.addPlayer(l_player2);
        d_playerList = d_GamePlayEngine.getPlayerList();
        d_distributeCountriesService = new DistributeCountriesService();
        // Distributes countries between players.
        d_distributeCountriesService.distributeCountries();

        d_player1 = d_playerList.get(0);
        d_player2 = d_playerList.get(1);
    }

    /**
     * Tests the "Deploy" order operation when the mentioned source country doesn't exist. This is expected to raise the
     * <code>EntityNotFoundException</code>.
     *
     * @throws EntityNotFoundException  Throws if entity not found.
     * @throws InvalidArgumentException Throws if the input is invalid.
     * @throws InvalidOrderException    Throws if exception while executing the order.
     */
    @Test(expected = EntityNotFoundException.class)
    public void testInvalidCountry() throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException{
        // Randomly passing any country name.
        DeployOrder l_deployOrder = new DeployOrder("INDIA", "10", d_player1);
        l_deployOrder.execute();
    }

    /**
     * Tests the "Deploy" order operation when the mentioned number of armies requested to be moved are invalid (for example: negative number). This is expected to raise the
     * InvalidInputException.
     *
     * @throws EntityNotFoundException  Throws if entity not found.
     * @throws InvalidArgumentException Throws if exception if any argument is invalid.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testInvalidNoOfArmies()
            throws EntityNotFoundException, InvalidArgumentException{
        // Passing negative number of armies to move.
        new DeployOrder("Mercury-South", "-10", d_player1);
    }

    /**
     * Tests the "Advance" operation when source and destination countries are owned by the player. This is expected to
     * add the number of armies. There will not be any battle.
     *
     * @throws EntityNotFoundException  Throws if entity not found.
     * @throws InvalidArgumentException Throws if the input is invalid.
     * @throws InvalidOrderException    Throws if exception while executing the order.
     */
    @Test(expected = Test.None.class)
    public void testCorrectDeployOrder()
            throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException{
        Country l_country = d_player1.getAssignedCountries().get(0);

        // Assign reinforcement to player 1.
        d_player1.setReinforcementCount(30);

        // Moving more armies than available armies.
        DeployOrder l_deployOrder = new DeployOrder(l_country.getCountryName(), "20", d_player1);
        l_deployOrder.execute();
        assertEquals(10, d_player1.getRemainingReinforcementCount());
    }
}