package com.APP.Project.UserCoreLogic.exceptions;

public class AbsentTagException extends VirtualMachineException {
     public AbsentTagException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     public AbsentTagException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
