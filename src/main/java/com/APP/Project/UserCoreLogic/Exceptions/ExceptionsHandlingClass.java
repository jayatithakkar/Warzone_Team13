package com.APP.Project.UserCoreLogic.exceptions;

import com.APP.Project.UserCoreLogic.UserCoreLogic;

/**
 * Catches exception in the current class
 *
 * @author Jayati Thakkar
 * @version 1.0
 */

public class ExceptionHandlingClass implements Thread.UncaughtExceptionHandler {

    public void uncaughtException(Thread t, Throwable e) {
        UserCoreLogic.getInstance().stderr("Something went wrong!");
    }
}
