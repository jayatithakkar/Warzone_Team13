package com.APP.Project.UserInterface.constants.states;
/**
 * This enum stores the state of the user interaction whether it is in wait state or is in progress
 *
 * @author Jayati Thakkar
 * @version 1.0
 */

public enum UserInteractionState {
    WAIT("wait"),
    IN_PROGRESS("in_progress"),

    GAME_ENGINE("game_engine");

    public String d_jsonValue;

    private UserInteractionState(String p_jsonValue) {
        this.d_jsonValue = p_jsonValue;
    }

    public String getJsonValue() {
        return d_jsonValue;
    }
}