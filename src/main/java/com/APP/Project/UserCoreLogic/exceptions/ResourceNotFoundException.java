package com.APP.Project.UserCoreLogic.exceptions;

/**
 * Exception indicating that a requested resource was not found.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class ResourceNotFoundException extends UserCoreLogicException {

    /**
     * Constructs a new ResourceNotFoundException with the specified error message.
     *
     * @param p_errorMessage the error message
     */
    public ResourceNotFoundException(String p_errorMessage) {
        super(p_errorMessage);
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified error message and cause.
     *
     * @param p_errorMessage       the error message
     * @param p_causeOfException   the cause of the exception
     */
    public ResourceNotFoundException(String p_errorMessage, Throwable p_causeOfException) {
        super(p_errorMessage, p_causeOfException);
    }
}
