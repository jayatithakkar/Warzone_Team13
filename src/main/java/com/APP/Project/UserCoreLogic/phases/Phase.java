package com.APP.Project.UserCoreLogic.phases;

import com.APP.Project.UserCoreLogic.GameEngine;
import com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;
import com.APP.Project.UserCoreLogic.gamePlay.services.*;
import com.APP.Project.UserCoreLogic.map_features.adapters.*;
import com.APP.Project.UserCoreLogic.exceptions.InvalidCommandException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Phase class represents an abstract phase in the game.
 * It provides methods to load, show, edit, validate, and save maps,
 * as well as methods to edit continents, countries, and neighbors,
 * set players, assign countries, and perform reinforcement, issuing orders,
 * fortification, ending the game, transitioning to the next state, and handling invalid commands.
 * This class also includes a method to invoke dynamic methods based on input parameters.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public abstract class Phase {
    
    GameEngine d_gameEngine;

    /**
     * Constructs a Phase object with a reference to the GameEngine.
     *
     * @param p_gameEngine The GameEngine object for this phase.
     */
    Phase(GameEngine p_gameEngine) {
        d_gameEngine = p_gameEngine;
    }

    /**
     * Loads a map based on the provided arguments.
     *
     * @param p_arguments The arguments needed for map loading.
     * @return A message indicating the result of the operation.
     * @throws UserCoreLogicException If an error occurs during map loading.
     */
    abstract public String loadMap(List<String> p_arguments) throws UserCoreLogicException;

    /**
     * Displays the current game map.
     *
     * @param p_arguments The arguments needed for displaying the map.
     * @return A message indicating the result of the operation.
     * @throws UserCoreLogicException If an error occurs during map display.
     */
    abstract public String showMap(List<String> p_arguments) throws UserCoreLogicException;

    /**
     * Edits the current game map.
     *
     * @param p_arguments The arguments needed for map editing.
     * @return A message indicating the result of the operation.
     * @throws UserCoreLogicException If an error occurs during map editing.
     */
    abstract public String editMap(List<String> p_arguments) throws UserCoreLogicException;

    /**
     * Edits continents in the game map.
     *
     * @param l_serviceType The type of editing service.
     * @param p_arguments   The arguments needed for continent editing.
     * @return A message indicating the result of the operation.
     * @throws UserCoreLogicException If an error occurs during continent editing.
     */
    abstract public String editContinent(String l_serviceType, List<String> p_arguments) throws UserCoreLogicException;

    /**
     * Edits countries in the game map.
     *
     * @param l_serviceType The type of editing service.
     * @param p_arguments   The arguments needed for country editing.
     * @return A message indicating the result of the operation.
     * @throws UserCoreLogicException If an error occurs during country editing.
     */
    abstract public String editCountry(String l_serviceType, List<String> p_arguments) throws UserCoreLogicException;

    /**
     * Edits neighbors for a country in the game map.
     *
     * @param l_serviceType The type of editing service.
     * @param p_arguments   The arguments needed for neighbor editing.
     * @return A message indicating the result of the operation.
     * @throws UserCoreLogicException If an error occurs during neighbor editing.
     */
    abstract public String editNeighbor(String l_serviceType, List<String> p_arguments) throws UserCoreLogicException;

    /**
     * Validates the current game map.
     *
     * @param p_arguments The arguments needed for map validation.
     * @return A message indicating the result of the operation.
     * @throws UserCoreLogicException If an error occurs during map validation.
     */
    abstract public String validateMap(List<String> p_arguments) throws UserCoreLogicException;

    /**
     * Saves the current game map.
     *
     * @param p_arguments The arguments needed for map saving.
     * @return A message indicating the result of the operation.
     * @throws UserCoreLogicException If an error occurs during map saving.
     */
    abstract public String saveMap(List<String> p_arguments) throws UserCoreLogicException;

    /**
     * Sets players in the game.
     *
     * @param l_serviceType The type of setting service.
     * @param p_arguments   The arguments needed for setting players.
     * @return A message indicating the result of the operation.
     * @throws UserCoreLogicException If an error occurs during player setting.
     */
    abstract public String setPlayers(String l_serviceType, List<String> p_arguments) throws UserCoreLogicException;

     /**
     * Assigns countries to players.
     *
     * @param p_arguments The arguments needed for assigning countries.
     * @return A message indicating the result of the operation.
     * @throws UserCoreLogicException If an error occurs during country assignment.
     */
    abstract public String assignCountries(List<String> p_arguments) throws UserCoreLogicException;

    /**
     * Executes the reinforcement phase of the game.
     *
     * @throws UserCoreLogicException If an error occurs during reinforcement phase.
     */
    abstract public void reinforce() throws UserCoreLogicException;

    /**
     * Executes the order issuing phase of the game.
     *
     * @throws UserCoreLogicException If an error occurs during order issuing phase.
     */
    abstract public void issueOrder() throws UserCoreLogicException;

    /**
     * Executes the fortification phase of the game.
     *
     * @throws UserCoreLogicException If an error occurs during fortification phase.
     */
    abstract public void fortify() throws UserCoreLogicException;

    /**
     * Ends the current game.
     *
     * @param p_arguments The arguments needed for ending the game.
     * @throws UserCoreLogicException If an error occurs during game ending.
     */
    abstract public void endGame(List<String> p_arguments) throws UserCoreLogicException;

    /**
     * Moves to the next game state.
     *
     * @throws UserCoreLogicException If an error occurs during state transition.
     */
    abstract public void nextState() throws UserCoreLogicException;

    /**
     * Throws an InvalidCommandException with a default message.
     *
     * @return A string representing an error message for an invalid command.
     * @throws UserCoreLogicException Exception indicating invalid user logic.
     */
    public String invalidCommand() throws UserCoreLogicException {
        throw new InvalidCommandException("Invalid command!");
    }

    /**
     * Invokes a method dynamically on a given target object with provided method name and argument values.
     *
     * @param p_target      The target object on which the method will be invoked.
     * @param p_methodName  The name of the method to be invoked.
     * @param p_argValues   The list of argument values to be passed to the method.
     * @return A string representing the result of the method invocation.
     * @throws UserCoreLogicException Exception indicating invalid user logic.
     */
    public String invokeMethod(Object p_target, String p_methodName, List<String> p_argValues) throws UserCoreLogicException {
        Class<?>[] l_valueTypes = new Class[p_argValues.size()];
        Object[] l_values = p_argValues.toArray();
        for (int l_argIndex = 0; l_argIndex < p_argValues.size(); l_argIndex++) {
            l_valueTypes[l_argIndex] = String.class;
        }
        try {
            Method l_methodReference = p_target.getClass().getMethod(p_methodName, l_valueTypes);
            return (String) l_methodReference.invoke(p_target, l_values);
        } catch (InvocationTargetException p_invocationTargetException) {
            throw new UserCoreLogicException(p_invocationTargetException.getCause().getMessage());
        } catch (NoSuchMethodException | IllegalAccessException p_e) {
            this.invalidCommand();
        }
        return null;
    }
}
