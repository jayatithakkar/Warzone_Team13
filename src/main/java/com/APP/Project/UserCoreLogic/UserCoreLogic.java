package com.APP.Project.UserCoreLogic;

import com.APP.Project.InterfaceCoreMiddleware;
import com.APP.Project.UserCoreLogic.exceptions.ExceptionsHandlingClass;
import com.APP.Project.UserInterface.constants.states.GamingStateInfo;
import com.APP.Project.UserCoreLogic.gamePlay.GameEngine;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * this redirects the user to different interaction state and stores the information of the game state and other game related features.
 *
 * @author Jayati Thakkar
 * @version 1.0
 */

public class UserCoreLogic {
    private static UserCoreLogic d_userCoreLogic;

    private GamingStateInfo d_gamingState = GamingStateInfo.NOT_STARTED;

    private InterfaceCoreMiddleware d_interfaceCoreMiddleware;

    private final ExecutorService d_executor = Executors.newFixedThreadPool(10);

    /**
     * it attaches the middleware for ther user interaction and the core logic.
     * @param p_userInterfaceMiddleware
     */
    public void attachUIMiddleware(InterfaceCoreMiddleware p_userInterfaceMiddleware) {
        d_interfaceCoreMiddleware = p_userInterfaceMiddleware;
    }

    /**
     * it returns an instance of <code>UserCoreLogic</code> class we created earlier.
     * @return the instance
     * @throws NullPointerException
     */
    public static UserCoreLogic getInstance() throws NullPointerException {
        if (d_userCoreLogic == null) {
            throw new NullPointerException("Virtual Machine was not created. Something went wrong.");
        }
        return d_userCoreLogic;
    }

    /**
     * it keeps track of the current game state
     *
     * @param p_gameState sets the variable to keep track of the game state
     */
    public void setGameState(GamingStateInfo p_gameState) {
        d_gamingState = p_gameState;
    }

    /**
     * it exists the entire engine.
     */
    public static void exit() {
        MAP_EDITOR_ENGINE().shutdown();
        GAME_PLAY_ENGINE().shutdown();
        UserCoreLogic.getInstance().stdout("Shutting the game down !!!");
    }

    /**
     * it returns the current game state information
     * @return current game state information
     */

    public GamingStateInfo getGameState() {
        return d_gamingState;
    }

    /**
     * Sets the game state to <code>PLAYING/code>
     */
    public void setGameStatePlaying() {
        this.setGameState(GamingStateInfo.PLAYING);
    }

    /**
     * it stores the information of map in run time
     * @return the instance of the same
     */
    public static MapFeatureEngine MAP_EDITOR_ENGINE() {
        return MapFeatureEngine.getInstance();
    }

    /**
     * it stores the information of game play in run time
     *
     * @return Value of the map editor engine.
     */
    public static GameEngine GAME_PLAY_ENGINE() {
        return GameEngine.getInstance();
    }

    /**
     * it initialises all the engines to store run time information
     */
    public void initialise() {
        // MAP_EDITOR ENGINE
        UserCoreLogic.MAP_EDITOR_ENGINE().initialise();
        // GAME_PLAY ENGINE
        UserCoreLogic.GAME_PLAY_ENGINE().initialise();
    }


    public Future<String> askForUserInput(String p_message) {
        return d_executor.submit(() ->
                d_interfaceCoreMiddleware.askForUserInput(p_message)
        );
    }

    /**
     * Sends the message to output channel of the user interface.
     *
     * @param p_message Represents the message.
     */
    public void stdout(String p_message) {
        d_interfaceCoreMiddleware.stdout(p_message);
    }

    /**
     * Sends the message to error channel of the user interface.
     *
     * @param p_message Represents the error message.
     */
    public void stderr(String p_message) {
        d_interfaceCoreMiddleware.stderr(p_message);
    }

    /**
     * It creates an instance of <code>UserCoreLogic</code> class.
     * @return the instance of the same class
     */
    public static UserCoreLogic newInstance() {
        d_userCoreLogic = new UserCoreLogic();
        // Default exception handler.
        ExceptionsHandlingClass l_exceptionHandler = new ExceptionsHandlingClass();
        Thread.setDefaultUncaughtExceptionHandler(l_exceptionHandler);
        return d_userCoreLogic;
    }
}
