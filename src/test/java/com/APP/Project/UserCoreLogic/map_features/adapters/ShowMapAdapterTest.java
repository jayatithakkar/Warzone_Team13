package  com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import com.APP.Project.UserCoreLogic.map_features.adapters.ShowMapAdapter;
import com.jakewharton.fliptables.FlipTable;
import  com.APP.Project.UserCoreLogic.exceptions.*;
import  com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test cases for the {@link ShowMapAdapter} class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class ShowMapAdapterTest {
    private ShowMapAdapter d_showMapService;

    /**
     * Setting up the context by loading the map file before testing the class methods.
     *
     * @throws AbsentTagException Throws if tag is absent in .map file.
     * @throws InvalidMapException Throws if map file is invalid.
     * @throws ResourceNotFoundException Throws if file not found.
     * @throws InvalidInputException Throws if provided argument and its value(s) are not valid.
     * @throws EntityNotFoundException Throws if entity not found while searching.
     * @throws URISyntaxException Throws if file name could not be parsed as a URI reference.
     * @throws IOException IOException
     */
    @BeforeClass
    public static void beforeClass() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException, URISyntaxException, IOException {
        EditMapAdapter l_editMapService = new EditMapAdapter();
        // Re-initialise map editor engine.
        MapFeatureEngine.getInstance().initialise();

        URL l_testFilePath = ShowMapAdapterTest.class.getClassLoader().getResource("test_map_files/test_map.map");
        assertNotNull(l_testFilePath);
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        String l_url = new URI(l_testFilePath.getPath()).getPath();
        l_editMapService.handleLoadMap(l_url);
    }

    /**
     * This method will initialise the ShowMapService object before running each test cases.
     *
     * @throws EntityNotFoundException Throws if required entity is not found.
     */
    @Before
    public void before() throws EntityNotFoundException {
        d_showMapService = new ShowMapAdapter();
    }

    /**
     * It tests the showContinentContent method which returns the String of continent information
     */
    @Test
    public void testShowContinentCountryContentTest() {
        String[] l_header = {"Continent Name", "Control Value", "Countries"};
        String[][] l_mapMatrix = {
                {"Earth", "10", "Earth-Atlantic,Earth-SouthAmerica,Earth-SouthPole"},
                {"Venus", "8", "Venus-East,Venus-South,Venus-Southwest"},
                {"Mercury", "6", "Mercury-East,Mercury-North,Mercury-South,Mercury-West"}
        };
        String l_mapTable = FlipTable.of(l_header, l_mapMatrix);
        String l_mapData = d_showMapService.showContinentCountryContent();
        assertNotNull(l_mapData);
        assertEquals(l_mapTable, l_mapData);
    }

    /**
     * Tests whether the found country list is actually a neighbour of the given country.
     */
    @Test
    public void testShowNeighbourCountriesTest() {
        String[][] l_neighbourMatrix = {
                {"COUNTRIES", "Mercury-South", "Mercury-East", "Mercury-West", "Mercury-North", "Venus-South", "Venus-East", "Venus-Southwest", "Earth-SouthPole", "Earth-SouthAmerica", "Earth-Atlantic"},
                {"Mercury-South", "X", "X", "X", "O", "O", "O", "O", "O", "O", "O"},
                {"Mercury-East", "X", "X", "X", "X", "O", "O", "O", "O", "O", "O"},
                {"Mercury-West", "X", "X", "X", "X", "O", "O", "O", "O", "O", "O"},
                {"Mercury-North", "O", "X", "X", "X", "X", "O", "O", "O", "O", "O"},
                {"Venus-South", "O", "O", "O", "X", "X", "X", "X", "O", "O", "O"},
                {"Venus-East", "O", "O", "O", "O", "X", "X", "X", "O", "O", "O"},
                {"Venus-Southwest", "O", "O", "O", "O", "X", "X", "X", "O", "O", "O"},
                {"Earth-SouthPole", "O", "O", "X", "O", "O", "O", "O", "X", "X", "O"},
                {"Earth-SouthAmerica", "O", "O", "O", "O", "O", "O", "X", "X", "X", "X"},
                {"Earth-Atlantic", "O", "O", "O", "O", "O", "O", "O", "O", "X", "X"}

        };
        String[] l_countryCountHeader = new String[l_neighbourMatrix.length];
        for (int i = 0; i < l_countryCountHeader.length; i++) {
            l_countryCountHeader[i] = "C" + i;
        }
        String l_countryData = d_showMapService.showNeighbourCountries();
        String l_countryTable = FlipTable.of(l_countryCountHeader, l_neighbourMatrix);
        assertNotNull(l_countryData);
        assertEquals(l_countryTable, l_countryData);
    }
}