package com.APP.Project.UserCoreLogic.exceptions;

//base class which handles different types of custom exceptions.
//@author Bhatt Bhoomi
public class CoreLogicException extends Exception {

    // parameterised constructor call parent constructor.
    public CoreLogicException(Throwable p_error) {
        super(p_error);
    }

    /*
     * parameterized constructor take an argument of type string and returns an
     * error message in string form.
     */
    public CoreLogicException(String p_message) {
        super(p_message);
    }

    // p_msg indicates error message
    // p_causeOfEception incates the reason of exception to occour.
    public CoreLogicException(String p_msg, Throwable p_causeOfEception) {
        super(p_msg, p_causeOfEception);
    }
}