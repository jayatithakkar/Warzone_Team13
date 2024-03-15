package com.APP.Project.UserCoreLogic.constants.interfaces;

/**
 * Defines the core functionalities for game engines responsible for managing
 * runtime information, including player data
 * and game state.
 * 
 * <pre>
 * Usage:
 * - To obtain an instance of a specific engine, use the static `getInstance` method provided by the implementing class.
 * - Ensure that the engine is properly initialized before starting the game loop.
 * - Invoke `shutdown` when the game is ending to properly release resources.
 * </pre>
 *
 * @author Bhoomiben Bhatt
 * @version 2.0
 */
public interface Engine {
     /**
      * Initializes the engine and its components
      */
     void initialise();

     /**
      * Shuts down the engine and cleanly exits from all associated threads.
      */
     void shutdown();
}
