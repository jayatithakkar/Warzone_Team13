package com.APP.Project.UserCoreLogic.Exceptions;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String p_errorMessage) {
        super(p_errorMessage);
    }

    public ResourceNotFoundException(String p_errorMessage, Throwable p_causeOfException) {
        super(p_errorMessage, p_causeOfException);
    }
}
