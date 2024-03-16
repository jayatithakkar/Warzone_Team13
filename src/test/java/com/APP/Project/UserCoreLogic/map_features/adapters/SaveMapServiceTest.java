package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.exceptions.ResourceNotFoundException;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Test cases for the map file to check if save is successful or not are contained in this class
 *
 * @author Rupal Kapoor
 */
public class SaveMapServiceTest {
    private static ContinentAdapter d_ContinentService;
    private static CountryAdapter d_CountryService;
    private static CountryNeighborAdapter d_CountryNeighbourService;
    private static SaveMapAdapter d_SaveMapService;
    private String testFile = "testing_save_file.map";

    /**
     * This attribute creates a temporary folder for the test case.
     */
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * This method runs before running of all the other test cases. It initializes different objects required to perform test.
     */
    @BeforeClass
    public static void before() {
        d_ContinentService = new ContinentAdapter();
        d_CountryService = new CountryAdapter();
        d_CountryNeighbourService = new CountryNeighborAdapter();
        d_SaveMapService = new SaveMapAdapter();
    }

    /**
     * This test method reinitializes the continent list before test case run.
     */
    @Before
    public void beforeTestCase() {
        MapFeatureEngine.getInstance().initialise();
    }

    /**
     * This test method adds the content to Continent List, Country List and Neighbour List.
     *
     * @throws InvalidInputException   is thrown for any invalid input other than the reqired parameters will throw this error.
     * @throws EntityNotFoundException is thrown for any Continent that is not found in the Continent List but added in the Country
     *                                 List will throw this error.
     */
    @Before
    public void addContentToTheMapFile() throws InvalidInputException, EntityNotFoundException{
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
     * This particular test method saves the content added in Continent, Country and Neighbour List into the .map file.
     *
     * @throws ResourceNotFoundException is thrown in case the Target File is not found  where content is to be saved
     * @throws InvalidInputException     is thrown in case the input is invalid.
     * @throws IOException               is thrown in case the IOException is generated.
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
