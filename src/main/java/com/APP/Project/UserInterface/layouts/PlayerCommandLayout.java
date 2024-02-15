package com.APP.Project.UserInterface.layouts;

import com.APP.Project.Main;
import com.APP.Project.UserInterface.constants.states.GamingState;
import com.APP.Project.UserInterface.exceptions.InvalidCommandException;
import com.APP.Project.UserInterface.layouts.commands.CommonCommand;
import com.APP.Project.UserInterface.layouts.commands.GamePlayCommand;
import com.APP.Project.UserInterface.layouts.commands.MapEditorCommand;
import com.APP.Project.UserInterface.models.PredefinedCommandList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class maps the command-layout classes to their game state. The class can
 * be used without creating any instance.
 */
public class PlayerCommandLayout {
    /**
     * The list of all classes
     */
    private static Map<GamingState, CommandLayout> d_GamePlayStateMap = new HashMap<>();

    /**
     * Object that stores user commands for map editor game state
     */
    private static final MapEditorCommand MAP_EDITOR_COMMAND_LAYOUT = new MapEditorCommand();

    /**
     * Object that stores user commands for gameplay state
     */
    private static final GamePlayCommand GAME_PLAY_COMMAND_LAYOUT = new GamePlayCommand();

    /**
     * Object that stores user commands for any game state
     */
    private static final CommonCommand COMMON_COMMAND_LAYOUT = new CommonCommand();

    /*
     * Stores the commands according to the game state
     */
    static {
        d_GamePlayStateMap.put(
                GamingState.MAP_EDITOR,
                PlayerCommandLayout.MAP_EDITOR_COMMAND_LAYOUT);

        d_GamePlayStateMap.put(
                GamingState.GAME_PLAY,
                PlayerCommandLayout.GAME_PLAY_COMMAND_LAYOUT);
    }

    /**
     * Gets matched the user command It decides the which list of predefined command
     * using the game state Then it
     * matches the user command with the head of the command provided
     *
     * @param p_keyOfCommand key that needs to matched to list of predefined
     *                       commands
     * @return Value that macthes between user command and key value
     * @throws InvalidCommandException If no command had found matching the provided
     *                                 head of the command.
     */
    public static PredefinedCommandList getUserCommand(String p_keyOfCommand) throws InvalidCommandException {
        // Gets the list of command from the layout, and then it is being streamed over
        // to filter the list
        List<PredefinedCommandList> l_globalCommandList = PlayerCommandLayout.findByKeyOfCommand(COMMON_COMMAND_LAYOUT,
                p_keyOfCommand);
        return l_globalCommandList.size() > 0 ? l_globalCommandList.get(0)
                : PlayerCommandLayout.getUsingKeyOfHead(d_GamePlayStateMap.get(Main.getGameState()),
                        p_keyOfCommand);
    }

    /**
     * Finds the first matched command from the predefined command list using the
     * key of the command
     * 
     * @param p_commandLayoutClass Represents the command sub class.
     * @param p_keyOfCommand       Value of the key to find the command
     * @return Value of found command
     */
    private static List<PredefinedCommandList> findByKeyOfCommand(CommandLayout p_commandLayoutClass,
            String p_keyOfCommand) {
        return p_commandLayoutClass.fetchUserCommands()
                .stream().filter((userCommand) -> userCommand.getHeadCommand().equals(p_keyOfCommand))
                .collect(Collectors.toList());

    }

    /**
     * Finds the first matched command from the predefined command list using the
     * key of the command
     * 
     * @param p_commandLayoutClass Represents the command sub class.
     * @param p_keyOfCommand       Value of the key to find the command
     * @return Value of found command
     * @throws InvalidCommandException If command not found in this gamestate
     */
    private static PredefinedCommandList getUsingKeyOfHead(CommandLayout p_commandLayoutClass, String p_keyOfCommand) {
        try {
            return PlayerCommandLayout.findByKeyOfCommand(p_commandLayoutClass, p_keyOfCommand).get(0);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            throw new InvalidCommandException("Unrecognized command!");
        }
    }

    /**
     * Gets the mappings between user-commands and java classnames for mapeditor
     * game state
     * 
     * @return the list of the mappings.
     */
    private static MapEditorCommand getMapEditorClassLayout() {
        return PlayerCommandLayout.MAP_EDITOR_COMMAND_LAYOUT;
    }

    /**
     * Gets the mappings between user-commands and java classnames for gameplay game
     * state
     * 
     * @return the list of the mappings.
     */
    private static GamePlayCommand getGamePlayClassLayout() {
        return PlayerCommandLayout.GAME_PLAY_COMMAND_LAYOUT;
    }

}
