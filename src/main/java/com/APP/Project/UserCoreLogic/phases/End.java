package com.APP.Project.UserCoreLogic.phases;

import com.APP.Project.UserCoreLogic.GameEngine;
import com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;

import java.util.List;

/**
 * Represents the ending phase of the game, where the game has concluded and no
 * further actions are permitted.
 * <p>
 * This class extends the {@link Phase} class, inheriting its basic structure
 * while implementing phase-specific
 * behavior. In the End phase, all game-related commands are considered invalid
 * as the game has already reached
 * its conclusion. This phase might be used to display final scores, declare a
 * winner, or perform any cleanup
 * operations before the game fully terminates.
 * </p>
 *
 * @author BHOOMIBEN BHATT
 * @version 1.0
 */
public class End extends Phase {
    /**
     * Constructs a new End phase for the specified game engine.
     *
     * @param p_gameEngine The game engine instance associated with this phase.
     */
    End(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * {@inheritDoc}
     * In the End phase, this command is considered invalid.
     */
    @Override
    public String loadMap(List<String> p_arguments) throws UserCoreLogicException {
        return invalidCommand();
    }

    // Similar JavaDoc entries for the other overridden methods...
    /**
     * {@inheritDoc}
     */
    @Override
    public String editMap(List<String> p_arguments) throws UserCoreLogicException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String showMap(List<String> p_arguments) throws UserCoreLogicException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editContinent(String l_serviceType, List<String> p_arguments) throws UserCoreLogicException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editCountry(String l_serviceType, List<String> p_arguments) throws UserCoreLogicException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editNeighbor(String l_serviceType, List<String> p_arguments) throws UserCoreLogicException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String saveMap(List<String> p_arguments) throws UserCoreLogicException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateMap(List<String> p_arguments) throws UserCoreLogicException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String setPlayers(String p_serviceType, List<String> p_arguments) throws UserCoreLogicException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String assignCountries(List<String> p_arguments) throws UserCoreLogicException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reinforce() throws UserCoreLogicException {
        invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void issueOrder() throws UserCoreLogicException {
        invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fortify() throws UserCoreLogicException {
        invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame(List<String> p_arguments) throws UserCoreLogicException {
        invalidCommand();
    }

    /**
     * {@inheritDoc}
     * In the End phase, attempting to transition to the next state is considered
     * invalid.
     */
    @Override
    public void nextState() throws UserCoreLogicException {
        invalidCommand();
    }
}
