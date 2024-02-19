package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.Container.CountryContainer;
import com.APP.Project.UserCoreLogic.Container.ContinentContainer;

import java.util.*;

public class CountryAdapter {
     private final MapFeatureEngine d_mapEditorEngine;

    
    private final ContinentContainer d_continentRepository;

    
    private final CountryContainer d_countryRepository;

    
    public CountryAdapter() {
        d_mapEditorEngine = MapFeatureEngine.getInstance();
        d_continentRepository = new ContinentContainer();
        d_countryRepository = new CountryContainer();
    }

    
    public String add(String p_countryName, String p_continentName) throws EntityNotFoundException {
        Country l_country = new Country(d_mapEditorEngine.getAllCountryList().size() + 1);
        l_country.setCountryName(p_countryName);

        Continent l_continent = d_continentRepository.searchFirstByContinentName(p_continentName);
        
        l_country.setInsideContinent(l_continent);

        l_continent.addCountry(l_country);

        return String.format("%s country added!", p_countryName);
    }

    public String add(Integer p_countryId, String p_countryName, Integer p_continentId) throws EntityNotFoundException {
        Country l_country = new Country(p_countryId);
        l_country.setCountryName(p_countryName);

        Continent l_continent = d_continentRepository.searchByContinentId(p_continentId);

        l_country.setInsideContinent(l_continent);

        l_continent.addCountry(l_country);

        return String.format("%s country added!", p_countryName);
    }

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
