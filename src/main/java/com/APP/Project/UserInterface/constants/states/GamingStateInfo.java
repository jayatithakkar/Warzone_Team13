package com.APP.Project.UserInterface.constants.states;

/**
 * @author Jayati Thakkar
 * @version 1.0
 */
public enum GamingStateInfo {
    MAP_EDITOR("map_editor"),
    PLAYING("playing");
    public String d_jsonValue;

    private GamingStateInfo(String p_jsonValue) {
        this.d_jsonValue = p_jsonValue;
    }

    public String getJsonValue() {
        return d_jsonValue;
    }
}
