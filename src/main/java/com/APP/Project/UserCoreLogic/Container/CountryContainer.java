package com.APP.Project.UserCoreLogic.Container;

import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;

import java.util.*;
import java.util.stream.Collectors;

/*
 * This class is responsible for searching country in different way like by it's name, id and neighbour
 * @author Bhoomiben Bhatt
 */
public class CountryContainer {
    /*
     * Search for country by it's name
     * p_countryName is name of country
     * returns matched countries
     */
    public List<Country> searchByCountryName(String p_countryName) {
        return MapFeatureEngine.getInstance().getAllCountryList().stream()
                .filter(p_country -> p_country.getUniqueCountryName().equals(p_countryName))
                .collect(Collectors.toList());
    }

    /*
     * search one country by it's name
     */
    public Country searchFirstByCountryName(String p_countryName) throws EntityNotFoundException {
        /*
         * Search for country by it's name
         * p_countryName is name of country
         * returns first matched country
         * throw an exception of EntityNotFound if searched entity has been not found.
         */
        List<Country> l_countryList = this.searchByCountryName(p_countryName);
        if (l_countryList.size() > 0)
            return l_countryList.get(0);
        throw new EntityNotFoundException(String.format("'%s' country not found", p_countryName));
    }

    /*
     * Search country by it's id
     */
    public Country searchByCountryId(Integer p_countryId) {
        /*
         * p_countryId is unique id of country
         * returns matched countries
         */
        List<Country> l_countries = MapFeatureEngine.getInstance().getAllCountryList().stream()
                .filter(p_country -> p_country.getUniqueCountryId().equals(p_countryId)).collect(Collectors.toList());
        if (!l_countries.isEmpty()) {
            return l_countries.get(0);
        } else {
            return null;
        }
    }

    /*
     * search countries whose neighbour is the parameter country
     */
    public List<Country> searchByNeighbourOfCountries(Country p_country) {
        /*
         * p_country is checked if it's neighbour to other countries or not
         * returns list of the countries.
         */
        return MapFeatureEngine.getInstance().getAllCountryList().stream()
                .filter(p_l_country -> !p_l_country.equals(p_country)
                        && p_l_country.getNeighborCountriesList().contains(p_country))
                .collect(Collectors.toList());
    }

    /*
     * Search for neighbouring country of given country
     */
    public List<Country> searchCountryNeighborsAndNotOwned(Country p_country) throws IllegalStateException {
        /*
         * return list of neighbouring countries
         * if returns empty list then this method wil throw an IllegalStateException
         */
        return p_country.getNeighborCountriesList().stream().filter((p_l_country) -> p_l_country.getOwnedBy() == null)
                .collect(Collectors.toList());
    }
}