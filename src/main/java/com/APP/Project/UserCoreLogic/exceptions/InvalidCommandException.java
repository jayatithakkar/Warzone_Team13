package com.APP.Project.UserCoreLogic.exceptions;

public class InvalidCommandException extends VirtualMachineException {
     public InvalidCommandException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     public InvalidCommandException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
