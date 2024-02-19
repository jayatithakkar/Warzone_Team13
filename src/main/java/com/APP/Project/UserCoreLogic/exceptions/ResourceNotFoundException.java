package com.APP.Project.UserCoreLogic.exceptions;

/**
 * Exception thrown when a requested resource is not found.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0 
 * 
 */
public class ResourceNotFoundException extends UserCoreLogicException {

    /**
     * Constructs a ResourceNotFoundException with the specified error message.
     *
     * @param p_errorMessage the error message describing the exception
     */
    public ResourceNotFoundException(String p_errorMessage) {
        super(p_errorMessage);
    }

    /**
     * Constructs a ResourceNotFoundException with the specified error message and cause.
     *
     * @param p_errorMessage      the error message describing the exception
     * @param p_causeOfException the cause of the exception
     */
    public ResourceNotFoundException(String p_errorMessage, Throwable p_causeOfException) {
        super(p_errorMessage, p_causeOfException);
    }
}
