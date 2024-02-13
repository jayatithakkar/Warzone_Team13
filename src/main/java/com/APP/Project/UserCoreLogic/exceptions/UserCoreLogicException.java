package com.APP.Project.UserCoreLogic.exceptions;

public class UserCoreLogicException extends Exception {
     public UserCoreLogicException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     public UserCoreLogicException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
