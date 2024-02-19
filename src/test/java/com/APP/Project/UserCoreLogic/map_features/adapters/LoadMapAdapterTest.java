package  com.APP.Project.UserCoreLogic.map_features.adapters;

import  com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import  com.APP.Project.UserInterface.constants.states.GamingStateInfo;
import  com.APP.Project.UserCoreLogic.exceptions.*;
import  com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the {@link LoadMapAdapter} class.
 *
 * @author Rikin Dipakkumar Chauhan
 */
public class LoadMapAdapterTest {
    private Main d_application;
    private URL d_testFilePath;
    private EditMapAdapter d_editMapAdapter;

    /**
     * Re-initializes required objects before test case run.
     */
    @Before
    public void beforeTestCase() {
        d_application = new Main();
        d_application.handleApplicationStartup();

        // Re-initialise map editor engine.
        MapFeatureEngine.getInstance().initialise();

        d_editMapAdapter = new EditMapAdapter();
        d_testFilePath = getClass().getClassLoader().getResource("map_files/solar.map");
    }

    /**
     * Tests the load map service method to check if the map file is being loaded correctly.
     *
     * @throws URISyntaxException Throws if URI syntax is invalid.
     * @see EditMapAdapter#handleLoadMap
     */
    @Test(expected = Test.None.class)
    public void testLoadMapService() throws URISyntaxException {
        // In Windows, URL will create %20 for space. To avoid, use the below logic.
        String l_url = new URI(d_testFilePath.getPath()).getPath();
        Main.VIRTUAL_MACHINE().setGameStatePlaying();
        assertEquals(Main.getGameState(), GamingStateInfo.PLAYING);
    }
}
