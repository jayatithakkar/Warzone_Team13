package com.APP.Project.UserInterface.layouts;

import com.APP.Project.Main;
import com.APP.Project.UserInterface.constants.states.GamingStateInfo;
import com.APP.Project.UserInterface.exceptions.InvalidCommandException;
import com.APP.Project.UserInterface.layouts.classes.CommonClass;
import com.APP.Project.UserInterface.layouts.classes.GamePlayClass;
import com.APP.Project.UserInterface.layouts.classes.MapEditorClass;

import java.util.HashMap;
import java.util.Map;

// This class maps classlayout to player game states
public class PlayerClassLayout {
    /**
     * Complete class list of all game states
     */
    private static Map<GamingStateInfo, ClassLayout> d_GamePlayStateMap = new HashMap<>();

    /**
     * Object that stores user commands for map editor game state
     */
    private static final MapEditorClass MAP_EDITOR_CLASS_LAYOUT = new MapEditorClass();

    /**
     * Object that stores user commands for gameplay state
     */
    private static final GamePlayClass GAME_PLAY_CLASS_LAYOUT = new GamePlayClass();

    /**
     * Object that stores user commands for any game state
     */
    private static final CommonClass COMMON_CLASS_LAYOUT = new CommonClass();

    /**
     * Stores the commands according to the game state
     */
    static {
        d_GamePlayStateMap.put(
                GamingStateInfo.MAP_EDITOR,
                PlayerClassLayout.MAP_EDITOR_CLASS_LAYOUT);

        d_GamePlayStateMap.put(
                GamingStateInfo.PLAYING,
                PlayerClassLayout.GAME_PLAY_CLASS_LAYOUT);
    }

    /**
     * Maps class name based on the key param
     *
     * @param p_keyOfCommand Value of key which maps command to locate classname
     * @return Value of p_keyOfCommand
     * @throws InvalidCommandException If no command had found matching the provided
     *                                 head of the command.
     */
    public static String GetClassName(String p_keyOfCommand) throws InvalidCommandException {
        try {
            // Using game state get classname
            // If key exists return classname
            return COMMON_CLASS_LAYOUT.fetchMappings().containsKey(p_keyOfCommand)
                    ? COMMON_CLASS_LAYOUT.fetchMappings().get(p_keyOfCommand)
                    : d_GamePlayStateMap.get(Main.getGameState()).fetchMappings().get(p_keyOfCommand);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            throw new InvalidCommandException("Unrecognized command!");
        }
    }

    /**
     * Gets the mappings between user-commands and java classnames for gameplay
     * state
     * 
     * @return the list of the mappings.
     */
    private static GamePlayClass getGamePlayClassLayout() {
        return PlayerClassLayout.GAME_PLAY_CLASS_LAYOUT;
    }

    /**
     * Gets the mappings between user-commands and java classnames for mapeditor
     * game state
     * 
     * @return the list of the mappings.
     */
    private static MapEditorClass getMapEditorClassLayout() {
        return PlayerClassLayout.MAP_EDITOR_CLASS_LAYOUT;
    }

}
