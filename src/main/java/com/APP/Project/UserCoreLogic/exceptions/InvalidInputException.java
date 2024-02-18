package com.APP.Project.UserCoreLogic.exceptions;

public class InvalidInputException extends UserCoreLogicException {

	public InvalidInputException(String p_errorMessage) {
		super(p_errorMessage);
	}

	public InvalidInputException(String p_errorMessage, Throwable p_causeOfException) {
		super(p_errorMessage, p_causeOfException);
	}
}
