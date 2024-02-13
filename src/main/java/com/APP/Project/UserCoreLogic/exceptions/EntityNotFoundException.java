package com.APP.Project.UserCoreLogic.exceptions;

public class EntityNotFoundException extends VirtualMachineException {

    public EntityNotFoundException(String p_exceptionMessage) {
        super(p_exceptionMessage);
    }

    public EntityNotFoundException(String p_exceptionMessage, Throwable p_exceptionCause) {
        super(p_exceptionMessage, p_exceptionCause);
    }

}
