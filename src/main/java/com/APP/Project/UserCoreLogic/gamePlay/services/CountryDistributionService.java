package com.APP.Project.UserCoreLogic.gamePlay.services;


import com.APP.Project.UserCoreLogic.GameEntities.Country;
import com.APP.Project.UserCoreLogic.GameEntities.Player;
import com.APP.Project.UserCoreLogic.containers.CountryContainer;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.Exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.gamePlay.GameEngine;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;

public class CountryDistributionService {

    /**
     * Captures the list of countries in a CountryRepository type object
     * to return the country based on the method used
     */
    private CountryContainer d_allCountriesRepository = new CountryContainer();

    /**
     * Captures all the list of countries
     */
    private List<Country> d_allCountriesList;

    private final GameEngine d_engine;

    /**
     * Default constructor for initalizing objects.
     */
    public CountryDistributionService() {
        d_allCountriesList = MapFeatureEngine.getInstance().getAllCountryList();
        d_engine = GameEngine.getInstance();
    }


    /**
     * This method returns the list of countries to be assigned to player.
     *
     * @param p_gamePlayerCountryCount Number of countries that are assigned to a player.
     * @param p_gamePlayer player Object passed.
     * @return The list of countries.
     */
    public List<Country> countryAssignment(Player p_gamePlayer, int p_gamePlayerCountryCount) {
        List<Country> l_allAssignedCountries = new ArrayList<>();
        List<Country> l_allCountriesList;
        List<Country> l_allGroupOfCountries;
        int l_uniquePlayerObjectCountryCount = p_gamePlayerCountryCount;

        int l_groupCountrySize;
        int l_count = 0;
        do {
            Country l_countrySelected = d_allCountriesList.get(l_count);
            if (l_countrySelected.getOwnedBy() == null) {
                l_countrySelected.setOwnedBy(p_gamePlayer);
                l_allGroupOfCountries = d_allCountriesRepository.findCountryNeighborsAndNotOwned(l_countrySelected);
                l_allGroupOfCountries.add(0, l_countrySelected);

                l_groupCountrySize = l_allGroupOfCountries.size();
                if (l_groupCountrySize < p_gamePlayerCountryCount) {
                    p_gamePlayerCountryCount -= l_groupCountrySize;
                    assignOwnerToCountry(p_gamePlayer, l_allGroupOfCountries);
                    l_allAssignedCountries.addAll(l_allGroupOfCountries);
                } else {
                    l_allCountriesList = l_allGroupOfCountries.subList(0, p_gamePlayerCountryCount);
                    assignOwnerToCountry(p_gamePlayer, l_allCountriesList);
                    l_allAssignedCountries.addAll(l_allCountriesList);
                }
            }
            l_count++;
            if (l_count >= d_allCountriesList.size()) {
                break;
            }
        } while (l_allAssignedCountries.size() < l_uniquePlayerObjectCountryCount);
        return l_allAssignedCountries;
    }
    /**
     * This function is used to assign countries to unique players.
     *
     * @return String response based on the request.
     * @throws InvalidInputException is thrown for the condition where no. of players equal zero.
     */
    public String countryDistribution() throws InvalidInputException {
        int l_allCountriesCount = d_allCountriesList.size();
        int l_uniquePlayerObjectCount = d_engine.getPlayerList().size();
        try {
            int l_countryByPlayerFloorValue = (int) floor(l_allCountriesCount / l_uniquePlayerObjectCount);
            int l_modValue = l_allCountriesCount % l_uniquePlayerObjectCount;

            for (Player l_uniquePlayerObject : d_engine.getPlayerList()) {
                if (l_modValue > 0) {
                    l_uniquePlayerObject.setAssignedCountryCount(l_countryByPlayerFloorValue + 1);
                    l_modValue--;
                } else {
                    l_uniquePlayerObject.setAssignedCountryCount(l_countryByPlayerFloorValue);
                }
            }
            for (Player l_uniquePlayerObject : d_engine.getPlayerList()) {
                int l_uniquePlayerObjectCountryCount = l_uniquePlayerObject.getAssignedCountryCount();
                List<Country> l_assignedCountryList = countryAssignment(l_uniquePlayerObject, l_uniquePlayerObjectCountryCount);
                l_uniquePlayerObject.setAssignedCountries(l_assignedCountryList);
            }
            return "The countries have been assigned successfully!";
        } catch (ArithmeticException e) {
            throw new InvalidInputException("The number of players equal zero");
        }
    }

    /**
     * This method calls the countryDistribution() method.
     *
     * @param p_allCommandValues Represents the values passed while running the command.
     * @return The success message for successful execution of method, else throws exception.
     * @throws InvalidInputException is thrown if the number of players equal zero.
     * @throws IllegalStateException is thrown when the method returns an empty list.
     */
    public String executeCommand(List<String> p_allCommandValues) throws IllegalStateException, EntityNotFoundException, InvalidInputException {
        // Below condition is to check if players have been added
        if (!GameEngine.getInstance().getPlayerList().isEmpty()) {
            String response = countryDistribution();
            GameEngine.getInstance().startGameLoop();
            return response;
        } else {
            throw new EntityNotFoundException("Kindly add the players to display game status!");
        }
    }

    /**
     * This method assigns the owner player to different countries.
     * @param p_allCountriesList List of countries
     * @param p_gamePlayer      Player object

     */
    public void assignOwnerToCountry(Player p_gamePlayer, List<Country> p_allCountriesList) {
        for (Country l_listOfCountries : p_allCountriesList) {
            l_listOfCountries.setOwnedBy(p_gamePlayer);
        }
    }



}

