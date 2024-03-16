package com.APP.Project.UserInterface;

import com.APP.Project.UserInterface.constants.UsersCommandsTest;
import com.APP.Project.UserInterface.mappers.UsersCommandsMapperTest;
import com.APP.Project.UserInterface.service.RequestsServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A test suite for the command-line interface package of the application.
 * This suite collectively runs all test cases related to user interface
 * functionalities,
 * including command interpretation, mapping, and execution. It serves as a
 * convenient
 * wrapper to execute multiple test classes in a specified order, ensuring
 * comprehensive
 * coverage of the user interface components' behavior.
 *
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        UsersCommandsTest.class,
        UsersCommandsMapperTest.class,
        RequestsServiceTest.class,
})
public class UserInterfaceTestSuite {
    // This class is used only as a holder for the annotation configuration.
    // It specifies which test classes are included in this suite.
}