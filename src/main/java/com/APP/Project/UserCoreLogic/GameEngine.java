package com.APP.Project.UserCoreLogic;

import com.APP.Project.UserCoreLogic.exceptions.ResourceNotFoundException;
import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;
import com.APP.Project.UserCoreLogic.logger.LogEntryBuffer;
import com.APP.Project.UserCoreLogic.logger.LogWriter;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.phases.Phase;
import com.APP.Project.UserCoreLogic.phases.Preload;

/**
 * This Game Engine creates an environment for the entire game for the user to play.
 *
 * @author Jayati Thakkar
 * @version 1.0
 */
public class GameEngine {
    /**
     * An instance of the same class
     */
    private static GameEngine d_Instance;

    /**
     * Phase object of the GameEngine state
     */
    private Phase d_gameState;

    LogEntryBuffer d_logEntryBuffer;
    LogWriter d_logWriter;

    /**
     * Gets the instance of the <code>GameEngine</code> class.
     *
     * @return Value of the instance.
     */
    public static GameEngine getInstance() throws NullPointerException {
        if (d_Instance == null) {
            d_Instance = new GameEngine();
            d_Instance.initialise();
        }
        return d_Instance;
    }

    /**
     * It initialises the engines to reset the runtime information.
     */
    public void initialise() {
        d_Instance.setGamePhase(new Preload(this));
        // MAP_EDITOR ENGINE
        GameEngine.MAP_EDITOR_ENGINE().initialise();
        // GAME_PLAY ENGINE
        GameEngine.GAME_PLAY_ENGINE().initialise();

        d_logEntryBuffer = LogEntryBuffer.getLogger();
        try {
            d_logWriter = new LogWriter(d_logEntryBuffer);
        } catch (ResourceNotFoundException p_e) {
            UserCoreLogic.getInstance().stderr("LogEntryBuffer failed!");
        }
    }

    /**
     * SShutsdown all the engines.
     */
    public void shutdown() {
        MAP_EDITOR_ENGINE().shutdown();
        GAME_PLAY_ENGINE().shutdown();
    }

    /**
     * Sets new phase for the game.
     *
     * @param p_gamePhase New value of the game phase.
     */
    public void setGamePhase(Phase p_gamePhase) {
        d_gameState = p_gamePhase;
    }

    /**
     * Returns the phase of game.
     *
     * @return Value of the game phase.
     */
    public Phase getGamePhase() {
        return d_gameState;
    }

    /**
     * Returns UserCoreLogic map-editor engine to store map information at runtime.
     *
     * @return Value of the map editor engine.
     */
    public static MapFeatureEngine MAP_EDITOR_ENGINE() {
        return MapFeatureEngine.getInstance();
    }

    /**
     * returns UserCoreLogic game-play engine to store runtime after the game starts.
     *
     * @return Value of the game-play engine.
     */
    public static GamePlayEngine GAME_PLAY_ENGINE() {
        return GamePlayEngine.getInstance();
    }
}