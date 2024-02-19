package  com.APP.Project.UserCoreLogic.gamePlay.services;

import com.APP.Project.UserCoreLogic.gamePlay.services.CountryDistributionService;
import com.APP.Project.UserCoreLogic.gamePlay.services.ReinforcementService;
import  com.APP.Project.UserCoreLogic.game_entities.Player;
import  com.APP.Project.UserCoreLogic.exceptions.*;
import  com.APP.Project.UserCoreLogic.gamePlay.GameEngine;
import  com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import  com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This Class will test the reinforced army as in the reinforced army is set correctly or not.
 *
 * @author Rutwik
 */
public class ReinforcementServiceTest {
    private static MapFeatureEngine d_MapEditorEngine;
    private static EditMapAdapter d_EditMapService;
    private static URL d_TestFile;
    private static CountryDistributionService d_DistributeCountriesService;
    private static ReinforcementService d_AssignReinforcementService;
    private static GameEngine d_GamePlayEngine;


    /**
     * Initializes different objects required to perform test.
     */
    @BeforeClass
    public static void beforeClass() {
        d_GamePlayEngine = GameEngine.getInstance();
        d_MapEditorEngine = MapFeatureEngine.getInstance();
        d_TestFile = ReinforcementServiceTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }


    /**
     * Setting up the required Objects before test run.
     *
     * @throws InvalidInputException Throws if provided argument and its value(s) are not valid.
     * @throws AbsentTagException Throws if tag is absent in .map file.
     * @throws InvalidMapException Throws if map file is invalid.
     * @throws ResourceNotFoundException Throws if file not found.
     * @throws EntityNotFoundException Throws if entity not found while searching.
     * @throws IOException IOException
     */
    @Before
    public void before() throws InvalidInputException, AbsentTagException, InvalidMapException, ResourceNotFoundException, EntityNotFoundException, IOException {
        d_GamePlayEngine.initialise();
        d_MapEditorEngine.initialise();
        d_MapEditorEngine.getAllCountryList();
        d_AssignReinforcementService = new ReinforcementService();

        Player l_player1 = new Player();
        Player l_player2 = new Player();

        d_GamePlayEngine.addPlayer(l_player1);
        d_GamePlayEngine.addPlayer(l_player2);

        d_EditMapService = new EditMapAdapter();
        d_EditMapService.handleLoadMap(d_TestFile.getPath());
        d_DistributeCountriesService = new CountryDistributionService();
        d_DistributeCountriesService.countryDistribution();
    }

    /**
     * This test will test the assign country list. It checks whether the assign country list is empty or not.
     */
    @Test(expected = Test.None.class)
    public void testAssignCountry() {
        for (Player l_player : GameEngine.getInstance().getPlayerList()) {
            assertNotNull(l_player.getAssignedCountries());
        }
    }

    /**
     * This Test will test the re-calculate reinforced army. It checks whether the army is reinforced properly or not.
     *
     * @throws EntityNotFoundException Throws if entity not found while searching.
     */
    @Test
    public void testingCalculatedReinforcedArmyValue() throws EntityNotFoundException {
        d_AssignReinforcementService.execute();
        int l_reinforcementArmies = GameEngine.getInstance().getPlayerList().get(0).getReinforcementCount();
        assertEquals(9, l_reinforcementArmies);

        int l_reinforcementArmies1 = GameEngine.getInstance().getPlayerList().get(1).getReinforcementCount();
        assertEquals(13, l_reinforcementArmies1);
    }
}
