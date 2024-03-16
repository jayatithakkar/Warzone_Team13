package com.APP.Project;
import com.APP.Project.UserInterface.UserInterfaceTestSuite;
import com.APP.Project.UserCoreLogic.UserCoreLogicTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A comprehensive test suite designed to execute all test cases related to both
 * the User Interface and User Core Logic components of the application. This
 * centralized test suite aggregates tests
 * from different areas of the application,
 * ensuring a holistic validation approach. By combining these suites, it
 * facilitates thorough testing across the
 * application's key functionalities, aiming to uncover any integration issues
 * and maintain high quality and reliability
 * of the application as a whole.
 * 
 * @author Bhoomiben bhatt
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserInterfaceTestSuite.class,
        UserCoreLogicTestSuite.class
})
public class ApplicationTestSuite {
    // This class remains empty, it is used only as a holder for the above
    // annotations.
}
