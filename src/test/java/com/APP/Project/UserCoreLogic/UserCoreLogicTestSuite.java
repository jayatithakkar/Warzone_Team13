package com.APP.Project.UserCoreLogic;

import com.APP.Project.UserCoreLogic.game_entities.EntityTestSuite;
import com.APP.Project.UserCoreLogic.gamePlay.GamePlayTestSuite;
import com.APP.Project.UserCoreLogic.map_features.MapEditorTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is the test suite for the user core logic test cases. It is basically a suite that combines a
 * sequential run of all the other test cases of different classes it entails.
 *
 * @author Rupal Kapoor
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        GamePlayTestSuite.class,
        MapEditorTestSuite.class,
        EntityTestSuite.class
})
public class UserCoreLogicTestSuite {
    // This class remains empty, it is used only as a holder for the above annotations.
}
