package com.APP.Project.UserCoreLogic.map_features;

import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.constants.interfaces.Engine;

import java.util.*;

/**
 * MapFeatureEngine class implements the Engine interface and provides functionalities related to map features.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class MapFeatureEngine implements Engine {
   
    private static MapFeatureEngine d_Instance;
    private List<Continent> d_continentList;

    private boolean d_isLoadingMap = false;

    /**
     * Retrieves the singleton instance of MapFeatureEngine.
     *
     * @return The singleton instance of MapFeatureEngine.
     */
    public static MapFeatureEngine getInstance() {
        if (d_Instance == null) {
            d_Instance = new MapFeatureEngine();
        }
        return d_Instance;
    }

    /**
     * Private constructor for singleton pattern. Initializes the map feature engine.
     */
    private MapFeatureEngine() {
        this.initialise();
    }

    /**
     * Initializes the continent list and resets serial numbers for continents and countries.
     */
    public void initialise() {
        d_continentList = new ArrayList<>();
        Continent.resetSerialNumber();
        Country.resetSerialNumber();
    }

    /**
     * Retrieves the list of continents.
     *
     * @return The list of continents.
     */
    public List<Continent> getContinentList() {
        return d_continentList;
    }

    /**
     * Sets the list of continents.
     *
     * @param p_continentList The list of continents to set.
     */
    public void setContinentList(List<Continent> p_continentList) {
        d_continentList = p_continentList;
    }

    /**
     * Retrieves the list of countries.
     *
     * @return The list of countries.
     */
    public ArrayList<Country> getCountryList() {
        ArrayList<Country> l_countries = new ArrayList<>();
        for (Continent l_continent : d_continentList) {
            for (Country l_country : l_continent.getCountryList()) {
                if (!l_countries.contains(l_country)) {
                    l_countries.add(l_country);
                }
            }
        }
        return l_countries;
    }

    /**
     * Retrieves the map of country IDs and their neighboring country IDs.
     *
     * @return The map of country IDs and their neighboring country IDs.
     */
    public Map<Integer, Set<Integer>> getCountryNeighbourMap() {
        Map<Integer, Set<Integer>> l_continentCountryMap = new HashMap<>();
        ArrayList<Country> l_countries = this.getCountryList();
        for (Country l_country : l_countries) {
            Set<Integer> l_neighborCountryIdList = new HashSet<>();
            for (Country l_neighborCountry : l_country.getNeighbourCountries()) {
                l_neighborCountryIdList.add(l_neighborCountry.getCountryId());
            }
            l_continentCountryMap.put(l_country.getCountryId(), l_neighborCountryIdList);
        }
        return l_continentCountryMap;
    }

    /**
     * Retrieves the map of continents and their respective countries.
     *
     * @return The map of continents and their respective countries.
     * @throws EntityNotFoundException If a continent does not have at least one country.
     */
    public Map<String, List<String>> getContinentCountryMap() throws EntityNotFoundException {
        Map<String, List<String>> l_continentCountryMap = new HashMap<>();
        for (Continent l_continent : d_continentList) {
            if (!l_continent.getCountryList().isEmpty()) {
                for (Country l_country : l_continent.getCountryList()) {
                    String continentName = l_continent.getContinentName();
                    List<String> l_countryNames;
                    if (l_continentCountryMap.containsKey(continentName)) {
                        l_countryNames = l_continentCountryMap.get(continentName);
                    } else {
                        l_countryNames = new ArrayList<>();
                    }
                    l_countryNames.add(l_country.getCountryName());
                    l_continentCountryMap.put(continentName, l_countryNames);
                }
            } else {
                throw new EntityNotFoundException("Add minimum one country in a continent!");
            }
        }
        return l_continentCountryMap;
    }

    /**
     * Adds a continent to the continent list.
     *
     * @param p_continent The continent to add.
     */
    public void addContinent(Continent p_continent) {
        d_continentList.add(p_continent);
    }

    /**
     * Retrieves the loading status of the map.
     *
     * @return True if the map is loading, false otherwise.
     */
    public boolean getLoadingMap() {
        return d_isLoadingMap;
    }

    /**
     * Sets the loading status of the map.
     *
     * @param p_loadingMap The loading status of the map.
     */
    public void setLoadingMap(boolean p_loadingMap) {
        d_isLoadingMap = p_loadingMap;
    }

    /**
     * Performs shutdown procedures.
     */
    public void shutdown() {
       
    }
}
