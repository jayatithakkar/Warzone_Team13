package com.APP.Project.UserCoreLogic.exceptions;

public class ResourceNotFoundException extends VirtualMachineException{
     public ResourceNotFoundException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     public ResourceNotFoundException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
