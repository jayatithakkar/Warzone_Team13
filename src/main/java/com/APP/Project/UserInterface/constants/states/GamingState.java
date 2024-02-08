package com.APP.Project.UserInterface.constants.states;

public enum GamingState {

    MAP_EDITOR("map_editor"),
    PLAYING("playing");

    public String d_jsonValue;

    private GamingState(String p_jsonValue) {
        this.d_jsonValue = p_jsonValue;
    }

    public String getJsonValue() {
        return d_jsonValue;
    }
}