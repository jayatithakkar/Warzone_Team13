package com.APP.Project.UserCoreLogic.Exceptions;

public class CoreLogicException extends Exception {

    public CoreLogicException(Throwable p_error) {
        super(p_error);
    }

    public CoreLogicException(String p_message) {
        super(p_message);
    }

    public CoreLogicException(String p_msg, Throwable p_causeOfEception) {
        super(p_msg, p_causeOfEception);
    }
}