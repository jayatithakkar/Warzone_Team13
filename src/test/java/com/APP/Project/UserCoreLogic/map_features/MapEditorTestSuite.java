package com.APP.Project.UserCoreLogic.map_features;

import com.APP.Project.UserCoreLogic.map_features.adapters.*;
//import com.warzone.team08.UserCoreLogic.map_features.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is the test suite for the map editor test cases. It is basically a suite that combines a
 * sequential run of all the other test cases of different classes it entails.
 *
 * @author Rupal Kapoor
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoadMapServiceTest.class,
        ShowMapServiceTest.class,
        ContinentServiceTest.class,
        ValidateMapServiceTest.class,
        CountryServiceTest.class,
        CountryNeighborServiceTest.class
})
public class MapEditorTestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}