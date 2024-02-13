package com.APP.Project.VirtualMachine.Exceptions;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String p_eMessage) {
        super(p_eMessage);
    }

    public ResourceNotFoundException(String p_eMessage, Throwable p_cause) {
        super(p_eMessage, p_cause);
    }
}
