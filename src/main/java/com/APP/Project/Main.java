package com.APP.Project;

import com.APP.Project.UserInterface.UserInterfaceClass;
import com.APP.Project.UserInterface.constants.states.GamingState;

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
    private static volatile boolean d_isRunning = true;

    /**
     * It will keep track of the game state
     */

    private static GamingState d_gameState = GamingState.MAP_EDITOR;

    /**
     * Checks if the game is in play or not
     *
     * @return False if user is playing; true otherwise
     */
    public static boolean isRunning() {
        return d_isRunning;
    }

    /**
     * sets d_isRunning False if user is playing; true otherwise
     *
     * @param d_isRunning
     */

    public static void setIsRunning(boolean d_isRunning) {
        d_isRunning = d_isRunning;
    }

    /**
     * sets the game set and keeps track of the same
     *
     * @param p_gameState
     */
    public static void setGameState(GamingState p_gameState) {
        d_gameState = p_gameState;
    }

    /**
     * gets the state of the game
     *
     * @return
     */
    public static GamingState getGameState() {
        return d_gameState;
    }


    public static void main(String[] args) throws InterruptedException {
        setIsRunning(true);

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
        Main.setIsRunning(false);
    }
}
