package com.APP.Project.UserCoreLogic.exceptions;

/**
 * Exception thrown when an invalid input is encountered in the User Core Logic module.
 * This exception is typically thrown when the input provided does not meet the expected criteria.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class InvalidInputException extends UserCoreLogicException {

	/**
     * Constructs an InvalidInputException with the specified error message.
     *
     * @param p_errorMessage the error message describing the reason for the exception
     */
	public InvalidInputException(String p_errorMessage) {
		super(p_errorMessage);
	}

	/**
     * Constructs an InvalidInputException with the specified error message and cause.
     *
     * @param p_errorMessage      the error message describing the reason for the exception
     * @param p_causeOfException the cause of the exception
     */
	public InvalidInputException(String p_errorMessage, Throwable p_causeOfException) {
		super(p_errorMessage, p_causeOfException);
	}
}
