package com.APP.Project.UserCoreLogic.exceptions;

public class ReinforcementOutOfBoundException extends VirtualMachineException{
     public ReinforcementOutOfBoundException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     public ReinforcementOutOfBoundException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
