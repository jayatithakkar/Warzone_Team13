package com.APP.Project.UserCoreLogic.constants.enums;
/**
 * This class contains different stages of the game loop.
 *
 * @author Rupal Kapoor
 */
public enum GamePlayStates {

    /**
     * This is the state for assigning the reinforcements.
     */
    ASSIGN_REINFORCEMENTS("assign_reinforcements"),
    /**
     * This is the state for executing the order from each player one by one.
     */
    EXECUTE_ORDER("execute_order"),
    /**
     * This is the state where the user has not entered the assigncountries.
     */
    NOT_AVAILABLE("not_available"),

    /**
     * This is used by the player to issue an order in this state.
     */
    ISSUE_ORDER("issue_order");

    /**
     * This variable captures the enum value.
     */

    public String d_jsonString;

    /**
     * This method sets the string value of the enum.
     *
     * @param p_enumValue Value of the enum.
     */
    private GamePlayStates(String p_enumValue) {
        this.d_jsonString = p_enumValue;
    }

    /**
     * This method captures the string value of the enum.
     *
     * @return the value of the enum.
     */
    public String getJsonValue() {
        return d_jsonString;
    }

}
