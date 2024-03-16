package com.APP.Project.UserCoreLogic.phases;

import com.APP.Project.UserCoreLogic.GameEngine;
import com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;
import com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import com.APP.Project.UserCoreLogic.map_features.adapters.ValidateMapAdapter;

import java.util.List;

/**
 * This class represents the Preload phase of map editing in the game's core logic.
 * It extends MapEditor and is responsible for preloading the map.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class Preload extends MapEditor {
    
    /**
     * Constructs a Preload object with the given GameEngine.
     *
     * @param p_gameEngine the GameEngine instance
     */
    public Preload(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Edits the map with the provided arguments.
     *
     * @param p_arguments the list of arguments for map editing
     * @return a string representing the result of the map editing operation
     * @throws UserCoreLogicException if an error occurs during map editing
     */
    @Override
    public String editMap(List<String> p_arguments) throws UserCoreLogicException {
        EditMapAdapter l_editMapService = new EditMapAdapter();
        String l_returnValue = l_editMapService.execute(p_arguments);
        d_gameEngine.setGamePhase(new PostLoad(d_gameEngine));
        return l_returnValue;
    }

    /**
     * Edits the continent with the specified service type and arguments.
     * This operation is not supported in the Preload phase.
     *
     * @param l_serviceType the service type for continent editing
     * @param p_arguments   the list of arguments for continent editing
     * @return a string indicating that the command is invalid
     */
    @Override
    public String editContinent(String l_serviceType, List<String> p_arguments) throws UserCoreLogicException {
        return this.invalidCommand();
    }

    /**
     * Edits the country with the specified service type and arguments.
     * This operation is not supported in the Preload phase.
     *
     * @param l_serviceType the service type for country editing
     * @param p_arguments   the list of arguments for country editing
     * @return a string indicating that the command is invalid
     */
    @Override
    public String editCountry(String l_serviceType, List<String> p_arguments) throws UserCoreLogicException {
        return this.invalidCommand();
    }

    /**
     * Edits the neighbor with the specified service type and arguments.
     * This operation is not supported in the Preload phase.
     *
     * @param l_serviceType the service type for neighbor editing
     * @param p_arguments   the list of arguments for neighbor editing
     * @return a string indicating that the command is invalid
     */
    @Override
    public String editNeighbor(String l_serviceType, List<String> p_arguments) throws UserCoreLogicException {
        return this.invalidCommand();
    }

    /**
     * Validates the map with the provided arguments.
     *
     * @param p_arguments the list of arguments for map validation
     * @return a string representing the result of the map validation operation
     * @throws UserCoreLogicException if an error occurs during map validation
     */
    @Override
    public String validateMap(List<String> p_arguments) throws UserCoreLogicException {
        ValidateMapAdapter l_validateMapService = new ValidateMapAdapter();
        return l_validateMapService.execute(p_arguments);
    }

    /**
     * Saves the map with the provided arguments.
     * This operation is not supported in the Preload phase.
     *
     * @param p_arguments the list of arguments for map saving
     * @return a string indicating that the command is invalid
     * @throws UserCoreLogicException if an error occurs during map saving
     */
    @Override
    public String saveMap(List<String> p_arguments) throws UserCoreLogicException {
        return invalidCommand();
    }

    /**
     * Moves to the next state.
     *
     * @throws UserCoreLogicException indicating that the map hasn't been loaded yet
     */
    @Override
    public void nextState() throws UserCoreLogicException {
        throw new UserCoreLogicException("Map hasn't been loaded.");
    }
}
