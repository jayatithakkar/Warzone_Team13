package com.APP.Project.UserInterface.constants.states;

public enum UserInteractionState {
    WAIT("wait"),
    IN_PROGRESS("in_progress");

    public String d_jsonValue;

    private UserInteractionState(String p_jsonValue) {
        this.d_jsonValue = p_jsonValue;
    }

    public String getJsonValue() {
        return d_jsonValue;
    }
}