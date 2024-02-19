package com.APP.Project.UserCoreLogic.map_features.adapters;

import  com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import  com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import  com.APP.Project.UserCoreLogic.exceptions.ResourceNotFoundException;
import  com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.map_features.adapters.ContinentAdapter;
import com.APP.Project.UserCoreLogic.map_features.adapters.CountryAdapter;
import com.APP.Project.UserCoreLogic.map_features.adapters.CountryNeighborAdapter;
import com.APP.Project.UserCoreLogic.map_features.adapters.SaveMapAdapter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Test cases for the {@link SaveMapAdapter} class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class SaveMapAdapterTest {
    private static ContinentAdapter d_ContinentService;
    private static CountryAdapter d_CountryService;
    private static CountryNeighborAdapter d_CountryNeighbourService;
    private static SaveMapAdapter d_SaveMapService;
    private String testFile = "testing_save_file.map";

    /**
     * Create temporary folder for test case.
     */
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * This method runs before the test case runs. This method initializes different objects required to perform test.
     */
    @BeforeClass
    public static void before() {
        d_ContinentService = new ContinentAdapter();
        d_CountryService = new CountryAdapter();
        d_CountryNeighbourService = new CountryNeighborAdapter();
        d_SaveMapService = new SaveMapAdapter();
    }

    /**
     * Re-initializes the continent list before test case run.
     */
    @Before
    public void beforeTestCase() {
        MapFeatureEngine.getInstance().initialise();
    }

    /**
     * This test will add content to Continent List, Country List and Neighbour List.
     *
     * @throws InvalidInputException   Any invalid input other than the reqired parameters will throw this error.
     * @throws EntityNotFoundException Any Continent that is not found in the Continent List but added in the Country
     *                                 List will throw this error.
     */
    @Before
    public void addContentToTheMapFile() throws InvalidInputException, EntityNotFoundException {
        d_ContinentService.add("Asia", "10");
        d_ContinentService.add("Australia", "15");
        d_CountryService.add("Delhi", "Asia");
        d_CountryService.add("Mumbai", "Asia");
        d_CountryService.add("Melbourne", "Australia");
        d_CountryNeighbourService.add("Delhi", "Mumbai");
        d_CountryNeighbourService.add("Mumbai", "Delhi");
        d_CountryNeighbourService.add("Melbourne", "Delhi");
    }

    /**
     * This test will save the content added in Continent, Country and Neighbour List into the .map file.
     *
     * @throws ResourceNotFoundException Throws if the Target File where content is to be saved is not found then this
     *                                   exception will be raised.
     * @throws InvalidInputException     Throws if input is invalid.
     * @throws IOException               Throws if IOException is generated.
     */
    @Test(expected = Test.None.class)
    public void testSaveFile() throws ResourceNotFoundException, InvalidInputException, IOException {
        // Create a temporary file.
        final File testFileObject = tempFolder.newFile(testFile);
        String response = d_SaveMapService.saveToFile(testFileObject);

        assert testFileObject.exists();
        assertNotNull(response);
    }

}
