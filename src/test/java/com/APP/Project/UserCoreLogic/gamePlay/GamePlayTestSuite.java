package  com.APP.Project.UserCoreLogic.gamePlay;

import  com.APP.Project.UserCoreLogic.gamePlay.services.ReinforcementServiceTest;
import  com.APP.Project.UserCoreLogic.gamePlay.services.CountryDistributionService;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.APP.Project.UserCoreLogic.gamePlay.services.DisplayMapServiceTest;

/**
 * Test suite for the game play functionality.
 * This suite includes tests for reinforcement service, country distribution service, and display map service.
 *
 * @author Rikin Dipakkumar Chauhan
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ReinforcementServiceTest.class, DisplayMapServiceTest.class
})
public class GamePlayTestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}
