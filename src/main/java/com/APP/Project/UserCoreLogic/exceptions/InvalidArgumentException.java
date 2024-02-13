package com.APP.Project.UserCoreLogic.exceptions;

public class InvalidArgumentException extends VirtualMachineException{
     public InvalidArgumentException(String p_exceptionMessage) {
        super(p_exceptionMessage);
    }

    public InvalidArgumentException(String p_exceptionMessage, Throwable p_exceptionCause) {
        super(p_exceptionMessage, p_exceptionCause);
    }
}
