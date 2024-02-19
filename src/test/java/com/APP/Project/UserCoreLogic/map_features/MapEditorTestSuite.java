package  com.APP.Project.UserCoreLogic.map_features;

import com.APP.Project.UserCoreLogic.map_features.adapters.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.APP.Project.UserCoreLogic.gamePlay.services.DisplayMapServiceTest;
import com.APP.Project.UserCoreLogic.map_features.adapters.*;

/**
 * Test suite for <code>MAP_EDITOR</code> test cases.
 *
 * @author Brijesh Lakkad
 * @version 1.0
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