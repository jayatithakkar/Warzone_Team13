package com.APP.Project;

import com.APP.Project.UserInterface.UserInterfaceClass;
import com.APP.Project.UserInterface.constants.states.GamingStateInfo;

/**
 * The execution of the game will start from this class
 *
 * @author Jayati Thakkar
 * @version 1.0
 */
public class Main {

    /**
     * If the user is playing, it will return false; true otherwise
     */
    private static volatile boolean d_ifRunning = true;

    /**
     * It will keep track of the game state
     */

    private static GamingStateInfo d_gameState = GamingStateInfo.MAP_EDITOR;

    /**
     * Checks if the game is in play or not
     *
     * @return False if user is playing; true otherwise
     */
    public static boolean isRunning() {
        return d_ifRunning;
    }

    /**
     * sets d_isRunning False if user is playing; true otherwise
     *
     * @param d_ifRunning
     */

    public static void setIfRunning(boolean d_ifRunning) {
        d_ifRunning = d_ifRunning;
    }

    /**
     * sets the game set and keeps track of the same
     *
     * @param p_gameState
     */
    public static void setGameState(GamingStateInfo p_gameState) {
        d_gameState = p_gameState;
    }

    /**
     * gets the state of the game
     *
     * @return
     */
    public static GamingStateInfo getGameState() {
        return d_gameState;
    }


    public static void main(String[] args) throws InterruptedException {
        setIfRunning(true);

        //An object of the interface for user to interact
        UserInterfaceClass l_userInteraction = new UserInterfaceClass();

        l_userInteraction.d_thread.start();

        //It waits until the game is over.
        l_userInteraction.d_thread.join();
    }

    /**
     * This is to exit the game
     */
    public static void exit() {
        Main.setIfRunning(false);
    }
}