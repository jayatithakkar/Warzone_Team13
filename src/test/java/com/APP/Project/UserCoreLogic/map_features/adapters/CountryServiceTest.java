package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * This class tests the add and remove operations on country.
 *
 * @author Raj Kumar Ramesh
 * @version 1.0.0
 */
public class CountryServiceTest {

    private static CountryAdapter d_CountryService;
    private List<Continent> d_continentList;
    private EditMapAdapter d_editMapService;
    private URL d_testFilePath;

    /**
     * Runs before the test case class runs; Initializes different objects required to perform test.
     */
    @BeforeClass
    public static void beforeTestClass() {
        d_CountryService = new CountryAdapter();
    }

    /**
     * Re-initializes the continent list before test case run.
     *
     * @throws AbsentTagException        Throws if any tag is missing in map file.
     * @throws InvalidMapException       Throws if map is invalid.
     * @throws ResourceNotFoundException Throws if file not found.
     * @throws InvalidInputException     Throws if input is invalid.
     * @throws EntityNotFoundException   Throws if entity not found.
     * @throws URISyntaxException        If error while parsing the string representing the path.
     */
    @Before
    public void beforeTestCase() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException, URISyntaxException {
        d_editMapService = new EditMapAdapter();
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_map.map");
        assertNotNull(d_testFilePath);
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapService.handleLoadMap(l_url);
        d_continentList = MapFeatureEngine.getInstance().getContinentList();
    }

    /**
     * Tests whether the wrong continent value is being shown or not.
     *
     * @throws EntityNotFoundException Throws if name of the continent which doesn't exists is provided.
     */
    @Test(expected = EntityNotFoundException.class)
    public void testInvalidContinentName() throws EntityNotFoundException{
        d_CountryService.add("India", "ABC");
    }

    /**
     * Tests whether the country is successfully added and removed or not; Passes if continent is removed.
     *
     * @throws EntityNotFoundException Throws if name of the continent which doesn't exists is provided.
     */
    @Test(expected = Test.None.class)
    public void testAddRemoveCountry()
            throws EntityNotFoundException{

        String l_continentName = d_continentList.get(0).getContinentName();
        String l_responseStringAddOp = d_CountryService.add("India", l_continentName);
        assertNotNull(l_responseStringAddOp);

        String l_responseStringRemoveOp = d_CountryService.remove("India");
        assertNotNull(l_responseStringRemoveOp);
    }
}