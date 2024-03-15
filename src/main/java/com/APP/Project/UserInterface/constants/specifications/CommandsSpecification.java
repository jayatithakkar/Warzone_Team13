package com.APP.Project.UserInterface.constants.specifications;

/**
 * Specification types to run command
 *
 * @author Raj Kumar Ramesh
 * @version 1.0
 */
public enum CommandsSpecification {
    /**
     * No Arguements needed for this command to run
     */
    CAN_RUN_ALONE,
    /**
     * Arguement values required for the command to run
     * For this specification, command layout will use ArgumentsSpecification to indicate # of arguments it needs to run
     */
    CAN_RUN_ALONE_WITH_VALUE,
    /**
     * One arguement and value atleast needed
     */
    AT_LEAST_ONE
}
