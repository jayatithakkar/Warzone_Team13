package com.APP.Project.UserInterface;

import com.APP.Project.UserInterface.constants.CommandsTestCases;
import com.APP.Project.UserInterface.mappers.UserCommandMapperTest;
import com.APP.Project.UserInterface.service.RequestServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suit for command-line interface package.
 *
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CommandsTestCases.class,
        UserCommandMapperTest.class,
        RequestServiceTest.class,
})
public class UserInterfaceTestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}
