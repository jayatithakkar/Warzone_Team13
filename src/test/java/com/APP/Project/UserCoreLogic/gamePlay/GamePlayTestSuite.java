package  com.APP.Project.UserCoreLogic.gamePlay;

import  com.APP.Project.UserCoreLogic.gamePlay.services.ReinforcementServiceTest;
import  com.APP.Project.UserCoreLogic.gamePlay.services.CountryDistributionService;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.APP.Project.UserCoreLogic.gamePlay.services.DisplayMapServiceTest;

/**
 * Test suite for <code>GAME_PLAY</code> test cases.
 *
 * @author CHARIT
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ReinforcementServiceTest.class,
        CountryDistributionService.class, DisplayMapServiceTest.class
})
public class GamePlayTestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}
