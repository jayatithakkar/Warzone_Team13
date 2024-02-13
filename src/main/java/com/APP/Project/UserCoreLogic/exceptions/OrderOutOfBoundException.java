package com.APP.Project.UserCoreLogic.exceptions;

public class OrderOutOfBoundException extends UserCoreLogicException {
     public OrderOutOfBoundException(String p_exceptionMessage) {
          super(p_exceptionMessage);
     }

     public OrderOutOfBoundException(String p_exceptionMessage, Throwable p_exceptionCause) {
          super(p_exceptionMessage, p_exceptionCause);
     }
}
