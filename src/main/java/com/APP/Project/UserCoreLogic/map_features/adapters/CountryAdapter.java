package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.logger.LogEntryBuffer;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.Container.ContinentContainer;
import com.APP.Project.UserCoreLogic.Container.CountryContainer;

import java.util.List;

/**
 * The CountryAdapter class provides methods to add and remove countries from the game map.
 * It interacts with the MapFeatureEngine, ContinentContainer, and CountryContainer to manage
 * countries and their associations with continents.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class CountryAdapter {
    
    private final MapFeatureEngine d_mapEditorEngine;

    private final ContinentContainer d_continentRepository;

    private final CountryContainer d_countryRepository;

    private final LogEntryBuffer d_logEntryBuffer;

    /**
     * Constructs a new CountryAdapter object.
     */
    public CountryAdapter() {
        d_mapEditorEngine = MapFeatureEngine.getInstance();
        d_continentRepository = new ContinentContainer();
        d_countryRepository = new CountryContainer();
        d_logEntryBuffer = LogEntryBuffer.getLogger();
    }

    /**
     * Adds a country to the map with the given name and continent.
     *
     * @param p_countryName   The name of the country to be added.
     * @param p_continentName The name of the continent to which the country belongs.
     * @return A message indicating the success of the operation.
     * @throws EntityNotFoundException if the specified continent is not found.
     */
    public String add(String p_countryName, String p_continentName) throws EntityNotFoundException {
        Country l_country = new Country(d_mapEditorEngine.getCountryList().size() + 1);
        l_country.setCountryName(p_countryName);

        Continent l_continent = d_continentRepository.findFirstByContinentName(p_continentName);
        l_country.setContinent(l_continent);

        l_continent.addCountry(l_country);
        if (!d_mapEditorEngine.getLoadingMap()) {
            d_logEntryBuffer.dataChanged("editcountry", "\n---EDITCOUNTRY---\n" + l_country.getCountryName() + " is added to the country list of" + l_continent.getContinentName() + "\n");
        }

        return String.format("%s country added!", p_countryName);
    }

    /**
     * Adds a country to the map with the given ID, name, and continent ID.
     *
     * @param p_countryId     The ID of the country to be added.
     * @param p_countryName   The name of the country to be added.
     * @param p_continentId   The ID of the continent to which the country belongs.
     * @return A message indicating the success of the operation.
     * @throws EntityNotFoundException if the specified continent is not found.
     */
    public String add(Integer p_countryId, String p_countryName, Integer p_continentId) throws EntityNotFoundException {
        Country l_country = new Country(p_countryId);
        l_country.setCountryName(p_countryName);

        Continent l_continent = d_continentRepository.findByContinentId(p_continentId);

        l_country.setContinent(l_continent);

        l_continent.addCountry(l_country);

        return String.format("%s country added!", p_countryName);
    }

    /**
     * Removes a country from the map with the given name.
     *
     * @param p_countryName The name of the country to be removed.
     * @return A message indicating the success of the operation.
     * @throws EntityNotFoundException if the specified country is not found.
     */
    public String remove(String p_countryName) throws EntityNotFoundException {
        Country l_country = d_countryRepository.findFirstByCountryName(p_countryName);
        l_country.getContinent().removeCountry(l_country);

        List<Country> l_neighborOfCountryList = d_countryRepository.findByNeighbourOfCountries(l_country);
        for (Country l_neighborOfCountry : l_neighborOfCountryList) {
            l_neighborOfCountry.removeNeighbourCountry(l_country);
        }
        if (!d_mapEditorEngine.getLoadingMap()) {
            d_logEntryBuffer.dataChanged("editcountry", "\n---EDITCOUNTRY---\n" + l_country.getCountryName() + " is removed to the country list of" + l_country.getContinent().getContinentName() + "\n");
        }

        return String.format("%s country removed!", p_countryName);
    }
}
