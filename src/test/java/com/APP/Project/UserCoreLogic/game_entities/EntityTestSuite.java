package com.APP.Project.UserCoreLogic.game_entities;

import com.APP.Project.UserCoreLogic.game_entities.orders.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is a test suite for the test cases of different concrete classes of various game_entities.
 *
 * @author Sushant Sinha
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdvanceOrderTest.class,
        AirliftOrderTest.class,
        BlockadeOrderTest.class,
        BombOrderTest.class,
        DeployOrderTest.class,
        PlayerTest.class,
        NegotiateOrderTest.class
})
public class EntityTestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}
