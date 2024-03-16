package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the adding and removing of neighbours of the country.
 *
 * @author Raj Kumar Ramesh
 */
public class CountryNeighborServiceTest {
    private static CountryNeighborAdapter d_CountryNeighbourService;
    private EditMapAdapter d_editMapService;
    private URL d_testFilePath;

    /**
     * Runs before the test case class runs.
     * Initializes different objects required to perform test.
     */
    @BeforeClass
    public static void beforeClass() {
        d_CountryNeighbourService = new CountryNeighborAdapter();
    }

    /**
     * Re-initializes the continent list before test case run.
     *
     * @throws UserCoreLogicException Throws exception generated during execution.
     * @throws URISyntaxException If error while parsing the string representing the path.
     */
    @Before
    public void beforeTestCase() throws UserCoreLogicException, URISyntaxException {
        MapFeatureEngine.getInstance().initialise();
        d_editMapService = new EditMapAdapter();
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_map.map");
        assertNotNull(d_testFilePath);
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapService.handleLoadMap(l_url);
    }

    /**
     * Tests if country and neighbours are valid
     *
     * @throws EntityNotFoundException Throws if data tag is absent in map file.
     */
    @Test(expected = EntityNotFoundException.class)
    public void testWrongCountryValues() throws EntityNotFoundException{
        //If both country name and neighbor country name are invalid(Do not exists in map file).
        d_CountryNeighbourService.add("ABC", "DEF");

        //If neighbor country name is invalid (Do not exists in map file).
        d_CountryNeighbourService.add("Mercury-South", "DEF");

        //If country name is invalid (Do not exists in map file).
        d_CountryNeighbourService.add("ABC", "Mercury-West");
    }

    /**
     * Tests whether the neighbour is successfully added and removed. Passes if added and then removed, otherwise
     * fails.
     *
     * @throws EntityNotFoundException Throws if data tag is absent in map file.
     */
    @Test(expected = Test.None.class)
    public void testAdd() throws EntityNotFoundException{

        String l_addResponse = d_CountryNeighbourService.add("Mercury-South", "Mercury-West");
        assertNotNull(l_addResponse);

        String l_removeResponse = d_CountryNeighbourService.remove("Mercury-South", "Mercury-West");

        assertNotNull(l_removeResponse);
    }
}