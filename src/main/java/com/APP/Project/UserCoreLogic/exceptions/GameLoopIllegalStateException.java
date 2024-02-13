package com.APP.Project.UserCoreLogic.exceptions;

public class GameLoopIllegalStateException extends VirtualMachineException{
     public GameLoopIllegalStateException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     public GameLoopIllegalStateException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
