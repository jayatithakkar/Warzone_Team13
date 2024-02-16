package com.APP.Project.UserCoreLogic.constants.enums;

/**
 * This enum lists all the orders which can be issued by the player
 *
 * @author Sushant Sinha
 */
public enum OrderTypes {
    /**
     * Order of deploying the reinforcements.
     */
    deploy("deploy");

    /**
     * String is the variable needed to set the enum value.
     */
    public String d_jsonValue;

    /**
     * Sets the string value of the enum.
     *
     * @param p_jsonValue Value of the enum.
     */
    private OrderTypes(String p_jsonValue) {
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
