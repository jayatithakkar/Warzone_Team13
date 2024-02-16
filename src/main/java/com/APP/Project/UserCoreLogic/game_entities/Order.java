package com.APP.Project.UserCoreLogic.game_entities;

import com.APP.Project.UserCoreLogic.constants.enums.OrderTypes;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidCommandException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidArgumentException;
import com.APP.Project.UserCoreLogic.containers.CountryContainer;
import com.APP.Project.UserCoreLogic.responses.CommandResponses;

/**
 * Getters and setters
 *
 * @author Sushant Sinha
 */
public class Order {
    private OrderTypes d_orderTypes;
    private Player d_ownerPlayer;
    private Country d_country;
    private int d_numOfReinforcements;

    /**
     * Identify the country using the provided name
     */
    private final static CountryContainer COUNTRY_CONTAINER = new CountryContainer();

    /**
     * Default constructor for Order.
     */
    public Order() {

    }

    /**
     * Get the order type.
     *
     * @return Value of the order type.
     */
    public OrderTypes getOrderType() {
        return d_orderTypes;
    }

    /**
     * Set the order type.
     *
     * @param p_orderTypes type of the order
     */
    public void setOrderType(OrderTypes p_orderTypes) {
        d_orderTypes = p_orderTypes;
    }

    /**
     * Get the country where the reinforcements are to be sent.
     *
     * @return target country.
     */
    public Country getCountryDetails() { return d_country; }

    /**
     * Sets the destination country for the reinforcements.
     *
     * @param p_country Value of the target country.
     */
    public void setCountryDetails(Country p_country) {
        d_country = p_country;
    }

    /**
     * Get the number of reinforcements.
     *
     * @return number of reinforcements.
     */
    public int getNumOfReinforcements() { return d_numOfReinforcements; }

    /**
     * Set the number of reinforcements.
     *
     * @param p_numOfReinforcements number of reinforcements
     */
    public void setNumOfReinforcements(int p_numOfReinforcements) {
        d_numOfReinforcements = p_numOfReinforcements;
    }

    /**
     * Get the player who issued the order
     *
     * @return Player who issued the order.
     */
    public Player getOwner() {
        return d_ownerPlayer;
    }

    /**
     * Set the player who issued the order
     *
     * @param p_ownerPlayer Player who issued the order.
     */
    public void setOwner(Player p_ownerPlayer) {
        d_ownerPlayer = p_ownerPlayer;
    }

    /**
     *
     * Static
     *
     * @param p_commandResponses object received as the user command input.
     * @throws EntityNotFoundException  Throws when the country is not found.
     * @throws InvalidCommandException  Throws when the command values are not absent.
     * @throws InvalidArgumentException Throws when the number of reinforcements is not a number.
     * @return Value of the new order created from user command response.
     */
    public static Order map(CommandResponses p_commandResponses)
            throws EntityNotFoundException,
            InvalidCommandException,
            InvalidArgumentException {
        Order l_newOrder = new Order();
        l_newOrder.setOrderType(OrderTypes.valueOf(p_commandResponses.getHeadCommand().toLowerCase()));
        try {
            Country l_targetCountry = COUNTRY_CONTAINER.findFirstByCountryName(p_commandResponses.getCommandValuesList().get(0));
            // Get country from repository.
            l_newOrder.setCountryDetails(l_targetCountry);
            try {
                l_newOrder.setNumOfReinforcements(Integer.parseInt(p_commandResponses.getCommandValuesList().get(1)));
            } catch (NumberFormatException p_e) {
                throw new InvalidArgumentException("Please provide a number for the reinforcements!");
            }
        } catch (ArrayIndexOutOfBoundsException p_e) {
            throw new InvalidCommandException("Command invalid!");
        }
        return l_newOrder;
    }

    /**
     * Executes the order during
     */
    public void execute() {
        if (this.getOrderType() == OrderTypes.deploy) {
            this.getCountryDetails().setNumberOfArmies(this.getCountryDetails().getNumberOfArmies() + this.getNumOfReinforcements());
        }
        this.getOwner().addExecutedOrder(this);
    }

    /**
     * Returns the string describing player order.
     *
     * @return String representing player orders.
     */
    @Override
    public String toString() {
        return String.format("%s player's order: %s %s %s", d_ownerPlayer.getName(), d_orderTypes, d_country.getUniqueCountryName(), d_numOfReinforcements);
    }
}
