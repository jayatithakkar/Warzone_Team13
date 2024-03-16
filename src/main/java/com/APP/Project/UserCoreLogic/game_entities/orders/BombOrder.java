package com.APP.Project.UserCoreLogic.game_entities.orders;

import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.exceptions.CardNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidOrderException;
import com.APP.Project.UserCoreLogic.logger.LogEntryBuffer;
import com.APP.Project.UserCoreLogic.Container.CountryContainer;
import com.jakewharton.fliptables.FlipTable;
import com.APP.Project.UserCoreLogic.constants.enums.CardType;
import com.APP.Project.UserCoreLogic.constants.enums.OrderTypes;
import com.APP.Project.UserCoreLogic.constants.interfaces.Card;
import com.APP.Project.UserCoreLogic.constants.interfaces.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the operations required to be perform when the Bomb card is used. When bomb card is used then
 * it simple halves the number of armies available in the country on which the bomb operation is performed. A player can
 * perform bomb operation on the countries owned by the other players only.
 *
 * @author Sushant Sinha
 */
public class BombOrder extends Order {
    private final Country d_targetCountry;
    private final Player d_owner;

    /**
     * To find the country using its data members.
     */
    private final CountryContainer d_countryRepository = new CountryContainer();

    private final LogEntryBuffer d_logEntryBuffer = LogEntryBuffer.getLogger();

    /**
     * Sets the country name and current player object.
     *
     * @param p_targetCountry Name of target country.
     * @param p_owner         Current player object.
     * @throws EntityNotFoundException Throws if the target country not found.
     */
    public BombOrder(String p_targetCountry, Player p_owner) throws EntityNotFoundException {
        // Get the target country from repository.
        d_targetCountry = d_countryRepository.findFirstByCountryName(p_targetCountry);
        d_owner = p_owner;
    }

    /**
     * Performs actual bomb operation.
     *
     * @throws InvalidOrderException If the order can not be performed due to an invalid country, an invalid number of
     *                               armies, or other invalid input.
     * @throws CardNotFoundException Card doesn't found in the player's card list.
     */
    public void execute() throws InvalidOrderException, CardNotFoundException {
        StringBuilder l_logResponse = new StringBuilder();
        l_logResponse.append("\n" + "Executing " + d_owner.getName() + " Order:" + "\n");
        Country l_country;
        List<Country> l_countryList;
        Card l_requiredCard;
        if (d_targetCountry.getOwnedBy() != d_owner) {
            l_requiredCard = d_owner.getCard(CardType.BOMB);
        } else {
            throw new InvalidOrderException("You have selected your own country to perform bomb operation. Please select opponent player's country");
        }

        if (d_owner.isNotNegotiation(d_targetCountry.getOwnedBy())) {
            List<Country> l_neighbourCountryList = new ArrayList<>();
            l_country = d_targetCountry;
            l_countryList = d_owner.getAssignedCountries();
            for (Country l_co : l_countryList) {
                l_neighbourCountryList.addAll(l_co.getNeighbourCountries());
            }
            if (l_neighbourCountryList.contains(l_country)) {
                int l_finalArmies = l_country.getNumberOfArmies() / 2;
                l_country.setNumberOfArmies(l_finalArmies);
                //Remove card from list
                d_owner.removeCard(l_requiredCard);
                l_logResponse.append(d_owner.getName() + " used Bomb card to half the army count of " + l_country.getCountryName() + "\n");
                String[] l_header = {"COUNTRY", "ARMY COUNT"};
                String[][] l_changeContent = {
                        {l_country.getCountryName(), String.valueOf(l_country.getNumberOfArmies())}
                };
                l_logResponse.append("\n Order Effect\n" + FlipTable.of(l_header, l_changeContent));
                d_logEntryBuffer.dataChanged("bomb", l_logResponse.toString());
            } else {
                throw new InvalidOrderException("Invalid Country Name is provided!! Country must be a neighboring country.");
            }
        }
    }

    /**
     * Returns the order type.
     *
     * @return Order type.
     */
    @Override
    public OrderTypes getType() {
        return OrderTypes.bomb;
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
        return String.format("%s %s", getType().getJsonValue(), d_targetCountry.getCountryName());
    }
}