package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.Container.CountryContainer;
import com.APP.Project.UserCoreLogic.Container.ContinentContainer;

import java.util.*;

/**
 * CountryAdapter class provides functionality to add and remove countries from continents.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class CountryAdapter {
    /**
     * Instance of MapFeatureEngine to access country list.
     */
     private final MapFeatureEngine d_mapEditorEngine;

    /**
     * Instance of ContinentContainer to manage continents.
     */
    private final ContinentContainer d_continentRepository;

    /**
     * Constructs a new CountryAdapter object with necessary repositories and engine instances.
     */
    private final CountryContainer d_countryRepository;

    /**
     * Constructs a new CountryAdapter and initializes necessary repositories and engines.
     */
    public CountryAdapter() {
        d_mapEditorEngine = MapFeatureEngine.getInstance();
        d_continentRepository = new ContinentContainer();
        d_countryRepository = new CountryContainer();
    }

    /**
     * Adds a new country to the specified continent.
     *
     * @param p_countryName   The name of the country to be added.
     * @param p_continentName The name of the continent where the country will be added.
     * @return A message indicating the country addition.
     * @throws EntityNotFoundException if the continent specified is not found.
     */
    public String add(String p_countryName, String p_continentName) throws EntityNotFoundException {
        Country l_country = new Country(d_mapEditorEngine.getAllCountryList().size() + 1);
        l_country.setCountryName(p_countryName);

        Continent l_continent = d_continentRepository.searchFirstByContinentName(p_continentName);
        
        l_country.setInsideContinent(l_continent);

        l_continent.addCountry(l_country);

        return String.format("%s country added!", p_countryName);
    }

    /**
     * Adds a new country to the specified continent.
     *
     * @param p_countryId     The ID of the country to be added.
     * @param p_countryName   The name of the country to be added.
     * @param p_continentId   The ID of the continent where the country will be added.
     * @return A message indicating the country addition.
     * @throws EntityNotFoundException if the continent specified is not found.
     */
    public String add(Integer p_countryId, String p_countryName, Integer p_continentId) throws EntityNotFoundException {
        Country l_country = new Country(p_countryId);
        l_country.setCountryName(p_countryName);

        Continent l_continent = d_continentRepository.searchByContinentId(p_continentId);

        l_country.setInsideContinent(l_continent);

        l_continent.addCountry(l_country);

        return String.format("%s country added!", p_countryName);
    }

    /**
     * Removes the specified country from its continent along with its neighbors.
     *
     * @param p_countryName The name of the country to be removed.
     * @return A message indicating the country removal.
     * @throws EntityNotFoundException if the country specified is not found.
     */
    public String remove(String p_countryName) throws EntityNotFoundException {
        Country l_country = d_countryRepository.searchFirstByCountryName(p_countryName);
        l_country.getInsideContinent().removeCountry(l_country);

        List<Country> l_neighborOfCountryList = d_countryRepository.searchByNeighbourOfCountries(l_country);
        for (Country l_neighborOfCountry : l_neighborOfCountryList) {
            l_neighborOfCountry.removeNeighborCountry(l_country);
        }

        return String.format("%s country removed!", p_countryName);
    }
}
