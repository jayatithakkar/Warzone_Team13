package com.APP.Project.UserCoreLogic.constants.interfaces;

import com.APP.Project.UserCoreLogic.constants.enums.OrderTypes;
import com.APP.Project.UserCoreLogic.exceptions.CardNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidOrderException;
import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;

/**
 * This interface provides the methods to be implemented by different orders.
 *
 * @author Bhoomiben Bhatt
 * @version 2.0
 */
public abstract class Order {
    /**
     * The execution index indicating when this order is supposed to be executed
     * within the game loop.
     */
    private final int d_executionIndex;
    /**
     * The index at which this order is considered expired and its effects can be
     * reversed or nullified.
     * It is initialized to -1 to indicate that by default, orders do not expire
     * immediately after execution.
     */
    private int d_expiryIndex = -1;

    /**
     * Constructs an Order and determines its execution and expiry indices based on
     * the order type.
     * <p>
     * For orders of type 'negotiate', special handling is applied to set the
     * execution and expiry indices
     * to ensure they are processed in a timely manner and their effects are
     * short-lived.
     * </p>
     */
    public Order() {
        if (this.getType() == OrderTypes.negotiate) {
            d_executionIndex = GamePlayEngine.getCurrentExecutionIndex() + 1;
            d_expiryIndex = d_executionIndex + 1;
            GamePlayEngine.getInstance().addFutureOrder(this);
        } else {
            d_executionIndex = GamePlayEngine.getCurrentExecutionIndex();
        }
    }

    /**
     * Executes the order logic during the {@code GameLoopState#EXECUTE_ORDER}
     * phase.
     * <p>
     * This method should contain the core logic for order execution, including any
     * game state modifications
     * and validations. Implementations must handle possible exceptions related to
     * invalid order parameters
     * or conditions.
     * </p>
     *
     * @throws InvalidOrderException If the order cannot be executed due to invalid
     *                               conditions.
     * @throws CardNotFoundException If required cards for the order are not found.
     */
    public abstract void execute() throws InvalidOrderException, CardNotFoundException;

    /**
     * Returns the type of the order as defined by the {@link OrderTypes} enum.
     *
     * @return The specific {@link OrderTypes} value representing this order's type.
     */
    public abstract OrderTypes getType();

    /**
     * Retrieves the execution index for this order.
     *
     * @return The index indicating when this order is scheduled for execution.
     */
    public int getExecutionIndex() {
        return d_executionIndex;
    }

    /**
     * Retrieves the expiration index for this order.
     *
     * @return The index after which this order's effects are considered expired, or
     *         -1 if the order does not expire.
     */
    public int getExpiryIndex() {
        return d_expiryIndex;
    }

    /**
     * Reverses the effect of this order or marks it as expired if it has previously
     * been executed.
     * <p>
     * This method is intended to be overridden by subclasses to implement logic for
     * undoing or nullifying
     * the effects of an order, typically used for orders with temporary effects.
     * </p>
     */
    abstract public void expire();
}
