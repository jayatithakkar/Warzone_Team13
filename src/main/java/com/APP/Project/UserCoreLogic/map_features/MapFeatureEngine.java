package com.APP.Project.UserCoreLogic.map_features;

import com.APP.Project.UserCoreLogic.constants.interfaces.Engine;
import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;

import java.util.*;

/**
 * MapFeatureEngine class represents the engine for managing map features.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 * 
 */
public class MapFeatureEngine implements Engine {

    /** The singleton instance of MapFeatureEngine. */
    private static MapFeatureEngine d_Instance;

    /** The list of continents in the map. */
    private List<Continent> d_continentList;

    /**
     * Retrieves the singleton instance of MapFeatureEngine.
     *
     * @return the singleton instance of MapFeatureEngine
     */
    public static MapFeatureEngine getInstance() {
        if (d_Instance == null) {
            d_Instance = new MapFeatureEngine();
        }
        return d_Instance;
    }

    /**
     * Private constructor to initialize the MapFeatureEngine.
     */
    private MapFeatureEngine() {
        this.initialise();
    }

    /**
     * Initializes the MapFeatureEngine by resetting continent and country counters.
     */
    public void initialise() {
        d_continentList = new ArrayList<>();
        Continent.resetCounter();
        Country.resetCounter();
    }

    /**
     * Retrieves the list of continents in the map.
     *
     * @return the list of continents
     */
    public List<Continent> getContinentList() {
        return d_continentList;
    }

    /**
     * Sets the list of continents in the map.
     *
     * @param p_continentList the list of continents to set
     */
    public void setContinentList(List<Continent> p_continentList) {
        d_continentList = p_continentList;
    }

    /**
     * Retrieves a list of all countries in the map.
     *
     * @return the list of all countries
     */
    public ArrayList<Country> getAllCountryList() {
        ArrayList<Country> l_countries = new ArrayList<>();
        for (Continent l_continent : d_continentList) {
            for (Country l_country : l_continent.getAllCountryList()) {
                if (!l_countries.contains(l_country)) {
                    l_countries.add(l_country);
                }
            }
        }
        return l_countries;
    }

    /**
     * Retrieves a map of country IDs and their neighbor country IDs.
     *
     * @return the map of country IDs and neighbor country IDs
     */
    public Map<Integer, Set<Integer>> getCountryNeighbourMap() {
        Map<Integer, Set<Integer>> l_continentCountryMap = new HashMap<>();
        ArrayList<Country> l_countries = this.getAllCountryList();
        for (Country l_country : l_countries) {
            Set<Integer> l_neighborCountryIdList = new HashSet<>();
            for (Country l_neighborCountry : l_country.getNeighborCountriesList()) {
                l_neighborCountryIdList.add(l_neighborCountry.getUniqueCountryId());
            }
            l_continentCountryMap.put(l_country.getUniqueCountryId(), l_neighborCountryIdList);
        }
        return l_continentCountryMap;
    }

    /**
     * Retrieves a map of continent names and lists of countries belonging to each continent.
     *
     * @return the map of continent names and lists of countries
     * @throws EntityNotFoundException if no countries are found in a continent
     */
    public Map<String, List<String>> getContinentCountryMap() throws EntityNotFoundException {
        Map<String, List<String>> l_continentCountryMap = new HashMap<>();
        for (Continent l_continent : d_continentList) {
            if (!l_continent.getAllCountryList().isEmpty()) {
                for (Country l_country : l_continent.getAllCountryList()) {
                    String continentName = l_continent.getContinentName();
                    List<String> l_countryNames;
                    if (l_continentCountryMap.containsKey(continentName)) {
                        l_countryNames = l_continentCountryMap.get(continentName);
                    } else {
                        l_countryNames = new ArrayList<>();
                    }
                    l_countryNames.add(l_country.getUniqueCountryName());
                    l_continentCountryMap.put(continentName, l_countryNames);
                }
            } else {
                throw new EntityNotFoundException("Add minimum one country in a continent!");
            }
        }
        return l_continentCountryMap;
    }

    /**
     * Adds a continent to the list of continents in the map.
     *
     * @param p_continent the continent to add
     */
    public void addContinent(Continent p_continent) {
        d_continentList.add(p_continent);
    }

    /**
     * Shuts down the MapFeatureEngine.
     */
    public void shutdown() {
    }
}
