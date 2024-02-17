package com.APP.Project.UserInterface.constants.states;

/**
 * This enum stores the current state of the game
 *
 * @author Jayati Thakkar
 * @version 1.0
 */
public enum GamingStateInfo {
    NOT_STARTED("not_started"),
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
