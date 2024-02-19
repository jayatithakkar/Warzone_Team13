package com.APP.Project.UserCoreLogic.exceptions;

public class ResourceNotFoundException extends UserCoreLogicException {

    public ResourceNotFoundException(String p_errorMessage) {
        super(p_errorMessage);
    }

    public ResourceNotFoundException(String p_errorMessage, Throwable p_causeOfException) {
        super(p_errorMessage, p_causeOfException);
    }
}
