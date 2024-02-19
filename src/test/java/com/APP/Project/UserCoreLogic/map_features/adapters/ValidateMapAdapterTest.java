package  com.APP.Project.UserCoreLogic.map_features.adapters;

import  com.APP.Project.UserCoreLogic.exceptions.InvalidMapException;
import  com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;
import com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import com.APP.Project.UserCoreLogic.map_features.adapters.ValidateMapAdapter;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test cases for the {@link ValidateMapAdapter} class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class ValidateMapAdapterTest {
    private ValidateMapAdapter d_validateMapService;
    private EditMapAdapter d_editMapAdapter;
    private URL d_testFilePath;

    /**
     * This method initialize the <code>EditMapAdapter</code> object for fetching file data in Validation.
     */
    @Before
    public void beforeTest() {
        d_editMapAdapter = new EditMapAdapter();
        d_validateMapService = new ValidateMapAdapter();
    }

    /**
     * Map validation - map is a connected graph.
     *
     * @throws URISyntaxException If the provided path had invalid characters.
     * @throws UserCoreLogicException        If there was an exception while loading the file.
     */
    @Test(expected = InvalidMapException.class)
    public void testMapIsConnectedGraph() throws UserCoreLogicException, URISyntaxException {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_map_connectedGraph.map");

        assertNotNull(d_testFilePath);
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapAdapter.handleLoadMap(l_url);

        d_validateMapService.execute(null);
    }

    /**
     * Continent validation - continent is a connected sub-graph.
     *
     * @throws URISyntaxException If the provided path had invalid characters.
     * @throws UserCoreLogicException        If there was an exception while loading the file.
     */
    @Test(expected = InvalidMapException.class)
    public void testContinentConnectedSubGraph() throws UserCoreLogicException, URISyntaxException {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        d_testFilePath = getClass().getClassLoader().getResource("test_map_files/test_continent_subgraph.map");

        assertNotNull(d_testFilePath);
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapAdapter.handleLoadMap(l_url);

        d_validateMapService.execute(null);
    }

    /**
     * It checks the ValidateMapService's execute method.
     *
     * @throws URISyntaxException If the provided path had invalid characters.
     * @throws UserCoreLogicException        If there was an exception while loading the file.
     */
    @Test
    public void testValidateMapService() throws UserCoreLogicException, URISyntaxException {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        d_testFilePath = getClass().getClassLoader().getResource("map_files/solar.map");

        assertNotNull(d_testFilePath);
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        d_editMapAdapter.handleLoadMap(l_url);

        String l_actualValue = d_validateMapService.execute(null);
        assertNotNull(l_actualValue);
    }
}