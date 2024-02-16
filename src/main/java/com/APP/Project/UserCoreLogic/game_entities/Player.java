package com.APP.Project.UserCoreLogic.game_entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.APP.Project.UserCoreLogic.UserCommandLogic;
import com.APP.Project.UserCoreLogic.constants.enums.OrderTypes;
import com.APP.Project.UserCoreLogic.responses.CommandResponses;
import com.APP.Project.UserCoreLogic.exceptions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.io.IOException;

/**
 * TThis class is for getter and setter methods specific to Player GameEntities
 *
 * @author Sushant Sinha
 */
public class Player {
    /**
     * Represents the (unique) name of each player.
     */
    private String d_uniquePlayerName;
    /**
     * Storing the list of orders issued by the player.
     */
    private final List<Order> d_ordersList;
    /**
     * Stores the list of orders executed by the GameEngine.
     */
    private final List<Order> d_executedOrdersList;
    private List<Country> d_assignedCountries;
    private int d_reinforcementsCountList;
    private int d_remainingReinforcementCounter;
    private boolean d_canReinforceArmy;
    private int d_assignedCountryCount;
    /**
     * Boolean field to check if the player has deployed the reinforcements or not.
     */
    private boolean d_hasDeployedReinforcements;

    /**
     * Initializing variables needed for handling the player state.
     */
    public Player() {
        d_ordersList = new ArrayList<>();
        d_executedOrdersList = new ArrayList<>();
        d_assignedCountries = new ArrayList<>();
        d_reinforcementsCountList = 0;
        d_remainingReinforcementCounter = 0;
        d_canReinforceArmy = true;
        d_assignedCountryCount = 0;
    }

    /**
     * Getter method for reinforcement count.
     *
     * @return reinforcement count.
     */
    public int getReinforcementCount() {
        return d_reinforcementsCountList;
    }

    /**
     * Setter method to assign reinforcement count.
     *
     * @param p_reinforcementsCount reinforcement count.
     */
    public void setReinforcementCount(int p_reinforcementsCount) {
        d_reinforcementsCountList = p_reinforcementsCount;
        // initialize the counter of remaining reinforcements.
        d_remainingReinforcementCounter = p_reinforcementsCount;
    }

    /**
     * Getter method for player name.
     *
     * @return player name.
     */
    public String getName() {
        return d_uniquePlayerName;
    }

    /**
     * Setter method for player name.
     *
     * @param p_name player name.
     */
    public void setName(String p_name) {
        this.d_uniquePlayerName = p_name;
    }

    /**
     * Setter method to assign countries.
     *
     * @return list of assigned countries.
     */
    public List<Country> getAssignedCountries() {
        return d_assignedCountries;
    }

    /**
     * Setter method to assign countries.
     *
     * @param p_assignedCountries list of assigned countries.
     */
    public void setAssignedCountries(List<Country> p_assignedCountries) {
        d_assignedCountries = p_assignedCountries;
    }

    /**
     * Adds assigned country to the list.
     *
     * @param p_assignedCountry Value of assigned countries.
     */
    public void addAssignedCountries(Country p_assignedCountry) {
        d_assignedCountries.add(p_assignedCountry);
    }

    /**
     * Gets the number of assigned countries for this player.
     *
     * @return count of countries.
     */
    public int getAssignedCountryCount() {
        return d_assignedCountryCount;
    }

    /**
     * Sets the number of countries that will be assigned to this player.
     *
     * @param p_assignedCountryCount Value of the count to be set.
     */
    public void setAssignedCountryCount(int p_assignedCountryCount) {
        d_assignedCountryCount = p_assignedCountryCount;
    }

    /**
     * Adds the order to the list of player's orders.
     *
     * @param p_order Order to be added.
     */
    public void addOrder(Order p_order) {
        d_ordersList.add(p_order);
    }

    /**
     * Gets the number of remaining reinforcements.
     *
     * @return Total number of remaining reinforcements.
     */
    public int getRemainingReinforcementCount() {
        return d_remainingReinforcementCounter;
    }

    /**
     * Sets the remaining reinforcement army
     *
     * @param p_remainingReinforcementCount reinforcement count
     */
    public void setRemainingReinforcementCount(int p_remainingReinforcementCount) {
        d_remainingReinforcementCounter = p_remainingReinforcementCount;
    }

    /**
     * Reduces the reinforcements. Checks if the player has already deployed the reinforcements.
     *
     * @return True if the player has deployed the reinforcements.
     */
    public boolean isHasDeployed() {
        return d_hasDeployedReinforcements;
    }

    /**
     * Sets the value indicating that if the player has deployed the reinforcements.
     *
     * @param p_hasDeployed Value of true if the player has deployed the reinforcements.
     */
    public void setHasDeployed(boolean p_hasDeployed) {
        d_hasDeployedReinforcements = p_hasDeployed;
    }

    /**
     * Generates the number of balance reinforcements for the player.
     * <p>Shows error if the player orders more than allowed
     * reinforcements than in possession. This method also sets the remaining number of reinforcements left for the
     * future use.
     *
     * @param p_usedReinforcementCount Total number of used reinforcements.
     * @return True if the player can reinforce the armies else false.
     */
    public boolean canPlayerReinforce(int p_usedReinforcementCount) {
        if (this.getRemainingReinforcementCount() == 0) {
            return false;
        }
        int l_remainingReinforcementCount = this.getRemainingReinforcementCount() - p_usedReinforcementCount;
        if (l_remainingReinforcementCount < 0) {
            return false;
        }
        this.setRemainingReinforcementCount(l_remainingReinforcementCount);
        return true;
    }

    /**
     * Gets order from the user and stores the order for the player.
     *
     * @throws ReinforcementOutOfBoundException If the player doesn't have enough reinforcement to issue the order.
     * @throws InvalidCommandException          If there is an error while preprocessing the user command.
     * @throws InvalidArgumentException         If the mentioned value is not of expected type.
     * @throws EntityNotFoundException          If the target country not found.
     * @throws ExecutionException               If any error while processing concurrent thread.
     * @throws InterruptedException             If scheduled thread was interrupted.
     */
    public void issueOrder() throws
            ReinforcementOutOfBoundException, InvalidCommandException, EntityNotFoundException, ExecutionException, InterruptedException, InvalidArgumentException {
        // Requests user interface for input from user.
        String l_responseVal = "";
        do {
            UserCommandLogic.getInstance().stdout(String.format("\nPlayer: %s\nUSAGE: Map status can be accessed using\n> showmap <enter>", this.d_uniquePlayerName, this.d_remainingReinforcementCounter));
            Future<String> l_responseOfFuture = UserCommandLogic.getInstance().askForUserInput(String.format("Enter Order:"));
            l_responseVal = l_responseOfFuture.get();
        } while (l_responseVal.isEmpty());
        try {
            ObjectMapper l_objectMapper = new ObjectMapper();
            CommandResponses l_commandResponses = l_objectMapper.readValue(l_responseVal, CommandResponses.class);
            Order l_newOrder = Order.map(l_commandResponses);
            if (l_newOrder.getOrderType() == OrderTypes.deploy) {
                if (this.getAssignedCountries().contains(l_newOrder.getCountryDetails())) {
                    if (this.getRemainingReinforcementCount() != 0 && this.canPlayerReinforce(l_newOrder.getNumOfReinforcements())) {
                        l_newOrder.setOwner(this);
                        this.addOrder(l_newOrder);
                    } else {
                        throw new ReinforcementOutOfBoundException("You don't have enough reinforcements.");
                    }
                } else {
                    throw new InvalidCommandException("You can deploy the reinforcements only in your assigned countries");
                }
            }
        } catch (IOException p_ioException) {
            throw new InvalidCommandException("Unrecognised input!");
        }
    }

    /**
     * Adds the order to the list of player's executed orders.
     *
     * @param p_executedOrder Executed order to be added.
     */
    public void addExecutedOrder(Order p_executedOrder) {
        d_executedOrdersList.add(p_executedOrder);
    }

    /**
     * Gets the list of executed orders.
     *
     * @return Value of the list of orders.
     */
    public List<Order> getExecutedOrders() {
        return d_executedOrdersList;
    }

    /**
     * Checks if the player has any order for execution.
     *
     * @return Value of true if the player has one or more orders.
     */
    public boolean hasOrders() {
        return d_ordersList.size() > 0;
    }

    /**
     * Gets the first order from the order list and removes the order from the list before returning it.
     *
     * @return Value of order to be executed.
     * @throws OrderOutOfBoundException If the player does not have any order.
     */
    public Order nextOrder() throws OrderOutOfBoundException {
        if (this.hasOrders())
            return d_ordersList.remove(0);
        else {
            throw new OrderOutOfBoundException("No order left for execution.");
        }
    }
}
