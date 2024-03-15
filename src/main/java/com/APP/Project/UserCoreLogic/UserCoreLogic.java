package com.APP.Project.UserCoreLogic;

import com.APP.Project.InterfaceCoreMiddleware;
import com.APP.Project.UserCoreLogic.phases.Phase;

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
    private static UserCoreLogic d_Instance;

    private InterfaceCoreMiddleware d_userInterfaceMiddleware;

    private final ExecutorService d_executor = Executors.newFixedThreadPool(10);

    /**
     * It creates an instance of <code>UserCoreLogic</code> class.
     * @return the instance of the same class
     */
    public static UserCoreLogic newInstance() {
        d_Instance = new UserCoreLogic();
        // Default exception handler.
//        ExceptionHandler l_exceptionHandler = new ExceptionHandler();
//        Thread.setDefaultUncaughtExceptionHandler(l_exceptionHandler);
        return d_Instance;
    }

    /**
     * it initialises all the engines to store run time information
     */
    public void initialise() {
        // Prepare instances.
        UserCoreLogic.GAME_ENGINE().initialise();
    }

    /**
     * it exists the entire engine.
     */
    public static void exit() {
        GAME_ENGINE().shutdown();
        UserCoreLogic.getInstance().stdout("Shutting down...");
    }

    /**
     * it attaches the middleware for ther user interaction and the core logic.
     * @param p_userInterfaceMiddleware
     */
    public void attachUIMiddleware(InterfaceCoreMiddleware p_userInterfaceMiddleware) {
        d_userInterfaceMiddleware = p_userInterfaceMiddleware;
    }

    /**
     * it returns an instance of <code>UserCoreLogic</code> class we created earlier.
     * @return the instance
     * @throws NullPointerException
     */
    public static UserCoreLogic getInstance() throws NullPointerException {
        if (d_Instance == null) {
            throw new NullPointerException("Virtual Machine was not created. Something went wrong.");
        }
        return d_Instance;
    }

    /**
     * it stores the information of game play in run time
     *
     * @return Value of the map editor engine.
     */
    public static GameEngine GAME_ENGINE() {
        return GameEngine.getInstance();
    }

    /**
     * It gets the state of the game
     *
     * @return Value of current game state
     */
    public static Phase getGamePhase() {
        return GAME_ENGINE().getGamePhase();
    }

    public Future<String> askForUserInput(String p_message) {
        return d_executor.submit(() ->
                d_userInterfaceMiddleware.askForUserInput(p_message)
        );
    }

    /**
     * Sends the message to output channel of the user interface.
     *
     * @param p_message Represents the message.
     */
    public void stdout(String p_message) {
        d_userInterfaceMiddleware.stdout(p_message);
    }

    /**
     * Sends the message to error channel of the user interface.
     *
     * @param p_message Represents the error message.
     */
    public void stderr(String p_message) {
        d_userInterfaceMiddleware.stderr(p_message);
    }

}
