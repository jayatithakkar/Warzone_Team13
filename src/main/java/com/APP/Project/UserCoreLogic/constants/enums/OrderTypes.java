package com.APP.Project.UserCoreLogic.constants.enums;

/**
 * Enumerates all the possible types of orders that a player can issue during
 * the "issue orders" phase of the game.
 * Each enum value corresponds to a specific command that players can enter
 * during the {@code GameLoop#ISSUE_ORDER} phase, representing
 * different strategic actions a player can take, such as moving armies,
 * deploying reinforcements, or engaging in diplomacy. The enum
 * facilitates the parsing and execution of these commands by providing a
 * predefined set of possible orders.
 *
 * @author Bhoomiben Bhatt
 * @version 2.0
 */
public enum OrderTypes {
    /**
     * If order type is advance.
     */
    advance("advance"),
    /**
     * If order type is airlift.
     */
    airlift("airlift"),
    /**
     * If order type is blockade.
     */
    blockade("blockade"),
    /**
     * If order type is bomb.
     */
    bomb("bomb"),
    /**
     * If order type is deploy.
     */
    deploy("deploy"),
    /**
     * If order type is negotiate.
     */
    negotiate("negotiate");

    /**
     * Variable to set enum value.
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
     * Retrieves the string representation of the enum value.
     * This method allows for the retrieval of the enum's command as a string,
     * facilitating the use of enum values in
     * contexts where a string representation is necessary, such as in user
     * interfaces or when parsing player commands.
     *
     * @return The string command associated with the order type.
     */
    public String getJsonValue() {
        return d_jsonValue;
    }
}
