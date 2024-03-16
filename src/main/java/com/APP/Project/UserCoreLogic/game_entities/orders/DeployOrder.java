package com.APP.Project.UserCoreLogic.game_entities.orders;

import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidArgumentException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidOrderException;
import com.APP.Project.UserCoreLogic.logger.LogEntryBuffer;
import com.APP.Project.UserCoreLogic.Container.CountryContainer;
import com.jakewharton.fliptables.FlipTable;
import com.APP.Project.UserCoreLogic.constants.enums.OrderTypes;
import com.APP.Project.UserCoreLogic.constants.interfaces.Order;

/**
 * This order represents the deploy order.
 *
 * @author Sushant Sinha
 */
public class DeployOrder extends Order {
    private final Player d_owner;
    private final Country d_targetCountry;
    private final int d_numOfArmies;
    private final LogEntryBuffer d_logEntryBuffer = LogEntryBuffer.getLogger();

    /**
     * To find the country using its data members.
     */
    private final CountryContainer d_countryRepository = new CountryContainer();

    /**
     * Default parameterised constructor.
     *
     * @param p_targetCountry Name of target country.
     * @param p_numOfArmies   Number of reinforcements to be deployed.
     * @param p_owner         Player who has initiated this order.
     * @throws EntityNotFoundException  Throws if the target country not found.
     * @throws InvalidArgumentException Throws if the number of armies is not a number.
     */
    public DeployOrder(String p_targetCountry, String p_numOfArmies, Player p_owner)
            throws EntityNotFoundException, InvalidArgumentException {
        // Get the target country from repository.
        d_targetCountry = d_countryRepository.findFirstByCountryName(p_targetCountry);
        try {
            d_numOfArmies = Integer.parseInt(p_numOfArmies);
            // Checks if the number of moved armies is less than zero.
            if (d_numOfArmies < 0) {
                throw new InvalidArgumentException("Specified number of armies can not be negative.");
            }
        } catch (NumberFormatException p_e) {
            throw new InvalidArgumentException(" Specified number of reinforcements is not a number.");
        }
        d_owner = p_owner;
    }

    /**
     * Executes the order during <code>GameLoopState#EXECUTE_ORDER</code>
     *
     * @throws InvalidOrderException If the order can not be performed due to an invalid country, an invalid number of
     *                               armies, or other invalid input.
     */
    public void execute() throws InvalidOrderException {
        StringBuilder l_logResponse = new StringBuilder();
        l_logResponse.append("\n" + "Executing " + d_owner.getName() + " Order:" + "\n");
        if (this.d_owner.getAssignedCountries().contains(d_targetCountry)) {
            int l_remainingReinforcementCount = d_owner.getRemainingReinforcementCount() - d_numOfArmies;
            if (l_remainingReinforcementCount < 0) {
                throw new InvalidOrderException("You don't have enough reinforcements.");
            }
            d_owner.setRemainingReinforcementCount(l_remainingReinforcementCount);
            d_targetCountry.setNumberOfArmies(this.d_targetCountry.getNumberOfArmies() + this.d_numOfArmies);
            d_owner.addExecutedOrder(this);
            l_logResponse.append("Deploying " + d_numOfArmies + " armies in the " + d_targetCountry.getCountryName() + "\n");
            String[] l_header = {"COUNTRY", "ARMY COUNT"};
            String[][] l_changeContent = {
                    {d_targetCountry.getCountryName(), String.valueOf(d_numOfArmies)}
            };
            l_logResponse.append("\n Order Effect\n" + FlipTable.of(l_header, l_changeContent));
            d_logEntryBuffer.dataChanged("deploy", l_logResponse.toString());
        } else {
            throw new InvalidOrderException("Reinforcements can only be deployed in your assigned countries");
        }
    }

    /**
     * Gets the type of order.
     *
     * @return Value of the order type.
     */
    public OrderTypes getType() {
        return OrderTypes.deploy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void expire() {
        // Does nothing.
    }

    /**
     * Returns the string describing player order.
     *
     * @return String representing player orders.
     */
    @Override
    public String toString() {
        return String.format("%s %s %s", getType().getJsonValue(), d_targetCountry.getCountryName(), d_numOfArmies);
    }
}
