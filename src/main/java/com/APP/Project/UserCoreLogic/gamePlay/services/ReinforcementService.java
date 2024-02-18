package com.APP.Project.UserCoreLogic.gamePlay.services;

import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.gamePlay.GameEngine;
import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This particular class is used to provide reinforcement
 * to the army to respective players at each of their turns.
 *
 * @author Rupal Kapoor
 */

public class ReinforcementService {

    /**
     * Map to maintain key continent and containing countries.
     */
    public Map<String, List<String>> d_allContinentCountryList;

   /**
    * Instance of MapFeatureEngine
    */
    public MapFeatureEngine d_mapEngine;

    /**
     * Default constructor to initialise values
     */
    public ReinforcementService() {
        d_mapEngine = MapFeatureEngine.getInstance();
    }

    /**
     * This method assigns each player the correct number of reinforcement armies
     * in accordance with Warzone rules.
     *
     * @throws EntityNotFoundException is thrown if player is not available.
     */
    public void execute() throws EntityNotFoundException {
        d_allContinentCountryList = d_mapEngine.getContinentCountryMap();

        for (Player l_gamePlayer : GameEngine.getInstance().getPlayerList()) {
            // this captures the continent count value
            int l_count = 0;
            for (Continent l_continent : d_mapEngine.getContinentList()) {

                List<String> l_allCountriesList = new ArrayList<>(d_allContinentCountryList.get(l_continent.getContinentName()));

                int l_returnContinentValue = checkPlayerOwnsContinent(l_gamePlayer, l_allCountriesList, l_continent);

                l_count = l_count + l_returnContinentValue;
            }

            int l_returnReinforcementArmy = addArmyReinforcement(l_gamePlayer, l_count);
            l_gamePlayer.setReinforcementCount(l_returnReinforcementArmy);
        }
    }

    /**
     * This method will check whether a player owns a whole continent or not. If a player owns then control value of
     * respective continent is returned otherwise zero will be returned.
     *
     * @param d_allPlayerList  captures all the Players
     * @param p_allCountriesList captures the list of Country of specific continent.
     * @param p_continent   the continent to which the country belongs.
     * @return this method returns the Continent's control value in case the player owns
     * entire continent else returns zero.
     */
    private int checkPlayerOwnsContinent(Player d_allPlayerList, List<String> p_allCountriesList, Continent p_continent) {
        List<String> l_country = new ArrayList<>();
        for (Country l_continentCountry : d_allPlayerList.getAssignedCountries()) {
            l_country.add(l_continentCountry.getUniqueCountryName());
        }
        boolean l_checkCountry = l_country.containsAll(p_allCountriesList);
        if (l_checkCountry) {
            return p_continent.getContinentControlValue();
        } else {
            return 0;
        }
    }

    /**
     * This method calculates the exact amount of army to be reinforced.
     *
     * @param p_continentValue the control value that has been added if a player owns a whole continent.
     * @param p_gamePlayer this is the player object
     * @return this method returns the army to be reinforced to the playe
     */
    private int addArmyReinforcement(Player p_gamePlayer, int p_continentValue) {
        int l_playerAssignedCountryCount = p_gamePlayer.getAssignedCountries().size();
        int l_armyReinforcement = Math.max(3, (int) Math.ceil(l_playerAssignedCountryCount / 3));

        l_armyReinforcement = l_armyReinforcement + p_continentValue;
        return l_armyReinforcement;
    }


}
