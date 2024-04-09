package com.APP.Project.UserCoreLogic.constants.enums;

/**
 * shows the type of file which can be saved/loaded.
 *
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
public enum FileType {
    MAP("map"),
    GAME("game");

    /**
     * Variable to set enum value.
     */
    public String d_jsonValue;

    /**
     * Sets the string value of the enum.
     *
     * @param p_jsonValue is Value of the enum.
     */
    private FileType(String p_jsonValue) {
        this.d_jsonValue = p_jsonValue;
    }

    /**
     * Gets the string value of the enum
     *
     * @return Value of the enum
     */
    public String getJsonValue() {
        return d_jsonValue;
    }
}
