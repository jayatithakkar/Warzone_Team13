package com.APP.Project.UserCoreLogic.map_features;

import com.APP.Project.UserCoreLogic.constants.interfaces.Engine;
import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;

import java.util.*;

public class MapFeatureEngine implements Engine {

    private static MapFeatureEngine d_Instance;

    private List<Continent> d_continentList;

    public static MapFeatureEngine getInstance() {
        if (d_Instance == null) {
            d_Instance = new MapFeatureEngine();
        }
        return d_Instance;
    }

    private MapFeatureEngine() {
        this.initialise();
    }

    public void initialise() {
        d_continentList = new ArrayList<>();
        Continent.resetCounter();
        Country.resetCounter();
    }

    public List<Continent> getContinentList() {
        return d_continentList;
    }

    public void setContinentList(List<Continent> p_continentList) {
        d_continentList = p_continentList;
    }

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

    public void addContinent(Continent p_continent) {
        d_continentList.add(p_continent);
    }

    public void shutdown() {
    }
}
