package  com.APP.Project.UserCoreLogic.map_features;

import com.APP.Project.UserCoreLogic.map_features.adapters.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.APP.Project.UserCoreLogic.gamePlay.services.DisplayMapServiceTest;
import com.APP.Project.UserCoreLogic.map_features.adapters.*;

/**
 * This class represents the test suite for map editing functionalities.
 * It includes tests for editing, loading, displaying maps, validating maps, managing continents,
 * countries, and their neighbors.
 *
 * @author Rikin Dipakkumar Chauhan
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EditMapAdapterTest.class,
        LoadMapAdapterTest.class,
        DisplayMapServiceTest.class,
        ContinentAdapterTest.class,
        ValidateMapAdapterTest.class,
        CountryAdapterTest.class,
        CountryNeighborAdapterTest.class
})
public class MapEditorTestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}