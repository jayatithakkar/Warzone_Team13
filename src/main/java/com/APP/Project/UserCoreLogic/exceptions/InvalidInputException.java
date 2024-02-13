package com.APP.Project.UserCoreLogic.exceptions;

public class InvalidInputException extends VirtualMachineException {
     public InvalidInputException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     public InvalidInputException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
