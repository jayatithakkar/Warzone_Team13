package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.exceptions.InvalidMapException;
import com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class entails test cases for the ValidateMapAdapter class which has different types of validation requires for Map.
 *
 * @autho Rupal Kapoor
  **/
public class ValidateMapServiceTest {
    private ValidateMapAdapter d_validateMapService;
    private EditMapAdapter d_editMapService;
    private URL d_testFilePath;

    /**
     * This test case method initialises the EditMapAdapter class object for fetching file data in Validation.
     */
    @Before
    public void beforeTest() {
        d_editMapService = new EditMapAdapter();
        d_validateMapService = new ValidateMapAdapter();
    }

    /**
     * This test case method validates if the map is a connected graph.
     *
     * @throws URISyntaxException is thrown in case the provided path had invalid characters.
     * @throws UserCoreLogicException is thrown in case there was an exception while loading the file.
     */
    @Test(expected = InvalidMapException.class)
    public void testMapIsConnectedGraph() throws UserCoreLogicException, URISyntaxException {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_map_connectedGraph.map");

        assertNotNull(d_testFilePath);
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapService.handleLoadMap(l_url);

        d_validateMapService.execute(null);
    }

    /**
     * This test case method checks if the continent is a connected sub-graph.
     *
     * @throws URISyntaxException is thrown in case the provided path had invalid characters.
     * @throws UserCoreLogicException is thrown in case there was an exception while loading the file.
     */
    @Test(expected = InvalidMapException.class)
    public void testContinentConnectedSubGraph() throws UserCoreLogicException, URISyntaxException {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_continent_subgraph.map");

        assertNotNull(d_testFilePath);
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapService.handleLoadMap(l_url);

        d_validateMapService.execute(null);
    }

    /**
     * This test case method checks the ValidateMapAdapter's execute method.
     *
     * @throws URISyntaxException is thrown in case the provided path had invalid characters.
     * @throws UserCoreLogicException is thrown in case there was an exception while loading the file.
     */
    @Test
    public void testValidateMapService() throws UserCoreLogicException, URISyntaxException {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        d_testFilePath = getClass().getClassLoader().getResource("map_files/solar.map");

        assertNotNull(d_testFilePath);
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapService.handleLoadMap(l_url);

        String l_actualValue = d_validateMapService.execute(null);
        assertEquals(l_actualValue,"Map validation passed successfully!");
    }
}