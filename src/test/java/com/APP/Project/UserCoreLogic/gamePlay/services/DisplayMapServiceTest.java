package  com.APP.Project.UserCoreLogic.gamePlay.services;

import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;
import  com.APP.Project.UserCoreLogic.game_entities.Player;
import  com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import  com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;

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
 * This file contains test cases for the DisplayMapService class.
 * @author Jayati Thakkar
 * @author Rikin Dipakkumar Chauhan
 */
public class DisplayMapServiceTest {
    private static MapFeatureEngine d_MapEditorEngine;
    private static EditMapAdapter d_EditMapService;
    private static URL d_TestFile;
    private static CountryDistributionService d_DistributeCountriesService;
    private static ReinforcementService d_AssignReinforcementService;
    private static GamePlayEngine d_GamePlayEngine;

    /**
     * Setting up the context by loading the map file before testing the class methods.
     */
    @BeforeClass
    public static void beforeClass() {

        d_GamePlayEngine = GamePlayEngine.getInstance();
        d_TestFile = ReinforcementServiceTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }

    /**
     * This method will initialise the ShowMapService object before running each test cases.
     *
     * @throws InvalidInputException     Throws if provided argument and its value(s) are not valid.
     * @throws EntityNotFoundException   Throws if entity not found while searching.
     * @throws URISyntaxException        Throws if file name could not be parsed as a URI reference.
     * @throws AbsentTagException        Throws if tag is absent in .map file.
     * @throws InvalidMapException       Throws if map file is invalid.
     * @throws ResourceNotFoundException Throws if file not found.
     * @throws InvalidInputException     Throws if provided argument and its value(s) are not valid.
     * @throws EntityNotFoundException   Throws if entity not found while searching.
     */
    @Before
    public void before() throws InvalidInputException, EntityNotFoundException, URISyntaxException, InvalidMapException, ResourceNotFoundException, AbsentTagException {
        // (Re)initialise the UserCoreLogic.
        d_GamePlayEngine.initialise();
        d_MapEditorEngine.initialise();
        d_MapEditorEngine.getCountryList();
        d_AssignReinforcementService = new ReinforcementService();

        Player l_player1 = new Player();
        Player l_player2 = new Player();

        d_GamePlayEngine.addPlayer(l_player1);
        d_GamePlayEngine.addPlayer(l_player2);

        d_EditMapService = new EditMapAdapter();
        assertNotNull(d_TestFile);
        String l_url = new URI(d_TestFile.getPath()).getPath();
        d_EditMapService.handleLoadMap(l_url);
        d_DistributeCountriesService = new CountryDistributionService();
        d_DistributeCountriesService.distributeCountries();
    }

    /**
     * It tests the if the assign country list is empty or not
     */
    @Test(expected = Test.None.class)
    public void testAssignCountry() {
        for (Player l_player : GamePlayEngine.getInstance().getPlayerList()) {
            assertNotNull(l_player.getAssignedCountries());
        }
    }

    /**
     * if will check if the reinforcement is calculated properly.
     *
     * @throws EntityNotFoundException if while searching, the entity is not found
     */
    @Test
    public void testingCalculatedReinforcedArmyValue() throws EntityNotFoundException {
        d_AssignReinforcementService.execute();
        int l_reinforcementArmies = GamePlayEngine.getInstance().getPlayerList().get(0).getReinforcementCount();
        assertEquals(9, l_reinforcementArmies);

        int l_reinforcementArmies1 = GamePlayEngine.getInstance().getPlayerList().get(1).getReinforcementCount();
        assertEquals(13, l_reinforcementArmies1);
    }
}
