package com.APP.Project.UserCoreLogic.exceptions;

public class InvalidMapException extends VirtualMachineException{
     public InvalidMapException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     public InvalidMapException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
