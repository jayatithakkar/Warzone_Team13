package  com.APP.Project.UserCoreLogic.map_features.adapters;

import  com.APP.Project.UserCoreLogic.game_entities.Continent;
import  com.APP.Project.UserCoreLogic.exceptions.*;
import  com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.map_features.adapters.CountryAdapter;
import com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test cases for the {@link CountryAdapter} class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class CountryAdapterTest {

    private static CountryAdapter d_CountryService;
    private List<Continent> d_continentList;
    private EditMapAdapter d_editMapAdapter;
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
     * @throws AbsentTagException         Throws if any tag is missing in map file.
     * @throws InvalidMapException        Throws if map is invalid.
     * @throws ResourceNotFoundException  Throws if file not found.
     * @throws InvalidInputException      Throws if input is invalid.
     * @throws EntityNotFoundException    Throws if entity not found.
     */
    @Before
    public void beforeTestCase() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException{
        d_editMapAdapter = new EditMapAdapter();
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_map.map");
        d_editMapAdapter.handleLoadMap(d_testFilePath.getPath());
        d_continentList = MapFeatureEngine.getInstance().getContinentList();
    }

    /**
     * Tests whether the wrong continent value is being shown or not.
     *
     * @throws EntityNotFoundException Throws if name of the continent which doesn't exists is provided.
     */
    @Test(expected = EntityNotFoundException.class)
    public void testInvalidContinentName() throws EntityNotFoundException {
        d_CountryService.add("India", "ABC");
    }

    /**
     * Tests whether the country is successfully added and removed or not; Passes if continent is removed.
     *
     * @throws EntityNotFoundException Throws if name of the continent which doesn't exists is provided.
     */
    @Test(expected = Test.None.class)
    public void testAddRemoveCountry()
            throws EntityNotFoundException {

        String l_continentName = d_continentList.get(0).getContinentName();
        String l_responseStringAddOp = d_CountryService.add("India", l_continentName);
        assertNotNull(l_responseStringAddOp);

        String l_responseStringRemoveOp = d_CountryService.remove("India");
        assertNotNull(l_responseStringRemoveOp);
    }
}