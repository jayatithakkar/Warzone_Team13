package  com.APP.Project.UserCoreLogic.map_features.adapters;

import  com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import  com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;
import  com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.map_features.adapters.CountryNeighborAdapter;
import com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * Test cases for the {@link CountryNeighborAdapter} class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class CountryNeighborAdapterTest {
    private static CountryNeighborAdapter d_CountryNeighbourService;
    private EditMapAdapter d_editMapAdapter;
    private URL d_testFilePath;

    /**
     * Runs before the test case class runs; Initializes different objects required to perform test.
     */
    @BeforeClass
    public static void beforeClass() {
        d_CountryNeighbourService = new CountryNeighborAdapter();
    }

    /**
     * Re-initializes the continent list before test case run.
     *
     * @throws UserCoreLogicException Exception generated during execution.
     */
    @Before
    public void beforeTestCase() throws UserCoreLogicException {
        MapFeatureEngine.getInstance().initialise();
        d_editMapAdapter = new EditMapAdapter();
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_map.map");
        assert d_testFilePath != null;
        d_editMapAdapter.handleLoadMap(d_testFilePath.getPath());
    }

    /**
     * Tests whether the country and neighbour country name are valid or not.
     *
     * @throws EntityNotFoundException Throws if data tag is absent in map file.
     */
    @Test(expected = EntityNotFoundException.class)
    public void testWrongCountryValues() throws EntityNotFoundException {
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
    public void testAdd() throws EntityNotFoundException {

        String l_addResponse = d_CountryNeighbourService.add("Mercury-South", "Mercury-West");
        assertNotNull(l_addResponse);

        String l_removeResponse = d_CountryNeighbourService.remove("Mercury-South", "Mercury-West");

        assertNotNull(l_removeResponse);
    }
}