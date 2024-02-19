package  com.APP.Project.UserCoreLogic.gamePlay.services;

import com.APP.Project.UserCoreLogic.gamePlay.services.CountryDistributionService;
import com.APP.Project.UserCoreLogic.gamePlay.services.DisplayMapService;
import com.APP.Project.UserCoreLogic.gamePlay.services.ManageGamePlayerService;
import com.APP.Project.UserCoreLogic.map_features.adapters.ShowMapAdapter;
import com.jakewharton.fliptables.FlipTable;
import  com.APP.Project.Main;
import  com.APP.Project.UserCoreLogic.UserCoreLogic;
import  com.APP.Project.UserCoreLogic.game_entities.Player;
import  com.APP.Project.UserCoreLogic.exceptions.*;
import  com.APP.Project.UserCoreLogic.gamePlay.GameEngine;
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
 * This test class tests the gamePlay's ShowMapService class functions
 *
 * @author MILESH
 */
public class DisplayMapServiceTest {
    private DisplayMapService d_showMapService;
    private List<Player> d_playerList;
    private static URL d_TestFilePath;

    /**
     * Setting up the context by loading the map file before testing the class methods.
     */
    @BeforeClass
    public static void beforeClass() {
        Main l_application = new Main();
        l_application.handleApplicationStartup();

        d_TestFilePath = DisplayMapServiceTest.class.getClassLoader().getResource("test_map_files/test_map.map");
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
        UserCoreLogic.getInstance().initialise();

        EditMapAdapter l_editMapService = new EditMapAdapter();
        d_showMapService = new DisplayMapService();
        assertNotNull(d_TestFilePath);
        String l_url = new URI(d_TestFilePath.getPath()).getPath();
        l_editMapService.handleLoadMap(l_url);

        CountryDistributionService l_distributeCountriesService = new CountryDistributionService();
        ManageGamePlayerService l_playerService = new ManageGamePlayerService();
        l_playerService.add("User_1");
        l_playerService.add("User_2");

        l_distributeCountriesService.countryDistribution();
        d_playerList = GameEngine.getInstance().getPlayerList();
    }

    /**
     * It tests the showPlayerContent method which returns the String of player information
     */
    @Test
    public void testShowPlayerContent() {
        String[] l_header1 = {"USER_1", "Mercury-South", "Mercury-East", "Mercury-West", "Mercury-North", "Venus-South"};
        String[] l_playerContent1 = {"Army Count", "0", "0", "0", "0", "0"};

        String l_PlayerExpectedData = FlipTable.of(l_header1, new String[][]{l_playerContent1});
        String l_playerActualData = d_showMapService.displayContentForPlayer(d_playerList.get(0));
        assertEquals(l_PlayerExpectedData, l_playerActualData);
    }
}