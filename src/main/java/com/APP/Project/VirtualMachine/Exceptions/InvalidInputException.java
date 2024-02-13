package com.APP.Project.VirtualMachine.Exceptions;

public class InvalidInputException extends Exceptions {

	public InvalidInputException(String p_eMessage) {
		super(p_eMessage);
	}

	public InvalidInputException(String p_eMessage, Throwable p_cause) {
		super(p_eMessage, p_cause);
	}
}
