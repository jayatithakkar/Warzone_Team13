package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.jakewharton.fliptables.FlipTable;
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
 * Test cases for the ShowMapAdapter class methods are contained in this class
 *
 * @author Rupal Kapoor
 */
public class ShowMapServiceTest {
    private ShowMapAdapter d_showMapService;

    /**
     * This method is used for setting up the context by loading the map file before testing other class methods.
     *
     * @throws AbsentTagException is thrown in case tag is absent in .map file.
     * @throws InvalidMapException is thrown in case map file is invalid.
     * @throws ResourceNotFoundException is thrown in case file not found.
     * @throws InvalidInputException is thrown in case provided argument and its values are not valid.
     * @throws EntityNotFoundException is thrown in case entity not found while searching.
     * @throws URISyntaxException is thrown in case file name could not be parsed as a URI reference.
     * @throws IOException IOException is thrown in case of file related issue occurs
     */
    @BeforeClass
    public static void beforeClass() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException, URISyntaxException, IOException {
        EditMapAdapter l_editMapService = new EditMapAdapter();
        // Re-initialise map editor engine.
        MapFeatureEngine.getInstance().initialise();

        URL l_testFilePath = ShowMapServiceTest.class.getClassLoader().getResource("test_map_files/test_map.map");
        assertNotNull(l_testFilePath);
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        String l_url = new URI(l_testFilePath.getPath()).getPath();
        l_editMapService.handleLoadMap(l_url);
    }

    /**
     * This test case method is used to initialise the ShowMapAdapter object before running each test cases.
     *
     * @throws EntityNotFoundException is thrown in case required entity is not found.
     */
    @Before
    public void before() throws EntityNotFoundException {
        d_showMapService = new ShowMapAdapter();
    }

    /**
     * This test case method tests the showContinentContent method which returns the String of continent information
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
     * This test case method tests whether the found country list is actually a neighbour of the given country.
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