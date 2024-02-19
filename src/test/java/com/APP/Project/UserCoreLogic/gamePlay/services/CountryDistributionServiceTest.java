package  com.APP.Project.UserCoreLogic.gamePlay.services;

import  com.APP.Project.Main;
import  com.APP.Project.UserCoreLogic.UserCoreLogic;
import  com.APP.Project.UserCoreLogic.game_entities.Player;
import  com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import  com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;
import  com.APP.Project.UserCoreLogic.gamePlay.GameEngine;
import  com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import com.APP.Project.UserCoreLogic.gamePlay.services.CountryDistributionService;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class contains test cases for the CountryDistributionService class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class CountryDistributionServiceTest {
    private static Main d_Main;
    private static URL d_TestFilePath;
    private CountryDistributionService d_distributeCountriesService;
    private static GameEngine d_GamePlayEngine;

    /**
     * Runs before the test case class runs; Initializes different objects required to perform test.
     */
    @BeforeClass
    public static void createPlayersList() {
        d_Main = new Main();
        d_Main.handleApplicationStartup();
        d_GamePlayEngine = GameEngine.getInstance();

        d_TestFilePath = CountryDistributionServiceTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }

    /**
     * Setting up the required objects before performing test.
     *
     * @throws UserCoreLogicException Exception generated during execution.
     */
    @Before
    public void before() throws UserCoreLogicException {
        // (Re)initialise the UserCoreLogic.
        UserCoreLogic.getInstance().initialise();

        // Loads the map
        EditMapAdapter l_editMapService = new EditMapAdapter();
        assert d_TestFilePath != null;
        l_editMapService.handleLoadMap(d_TestFilePath.getPath());

        Player l_player1 = new Player();
        Player l_player2 = new Player();

        l_player1.setName("User_1");
        l_player2.setName("User_2");

        d_GamePlayEngine.addPlayer(l_player1);
        d_GamePlayEngine.addPlayer(l_player2);

        d_distributeCountriesService = new CountryDistributionService();
    }

    /**
     * Tests whether the player list is empty or not. Passes if list contains players objects, otherwise fails.
     *
     * @throws InvalidInputException Throws if invalid player count.
     */
    @Test(expected = Test.None.class)
    public void testNumberOfPlayer() throws InvalidInputException {
        d_distributeCountriesService.countryDistribution();
    }

    /**
     * Tests whether the count of countries required to be assigned to the player is correct or not.
     *
     * @throws InvalidInputException Throws if player objects list is empty.
     */
    @Test(expected = Test.None.class)
    public void testPlayerCountryCount() throws InvalidInputException {
        d_distributeCountriesService.countryDistribution();
        assertEquals(5, d_GamePlayEngine.getPlayerList().get(0).getAssignedCountryCount());
    }

    /**
     * Test whether the countries are correctly assigned or not.
     *
     * @throws InvalidInputException Throws if player objects list is empty.
     */
    @Test(expected = Test.None.class)
    public void testAssignedCountriesCount() throws InvalidInputException {
        String l_response = d_distributeCountriesService.countryDistribution();
        assertNotNull(l_response);

        int l_size = d_GamePlayEngine.getPlayerList().get(0).getAssignedCountries().size();
        assertEquals(5, l_size);
    }
}