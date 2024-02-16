package com.APP.Project.UserCoreLogic.Container;

import com.APP.Project.UserCoreLogic.GameEntities.Country;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;

import java.util.*;
import java.util.stream.Collectors;

public class CountryContainer {
    public List<Country> searchByCountryName(String p_countryName) {
        return MapFeatureEngine.getInstance().getAllCountryList().stream()
                .filter(p_country -> p_country.getUniqueCountryName().equals(p_countryName))
                .collect(Collectors.toList());
    }

    public Country searchFirstByCountryName(String p_countryName) throws EntityNotFoundException {
        List<Country> l_countryList = this.searchByCountryName(p_countryName);
        if (l_countryList.size() > 0)
            return l_countryList.get(0);
        throw new EntityNotFoundException(String.format("'%s' country not found", p_countryName));
    }

    public Country searchByCountryId(Integer p_countryId) {
        List<Country> l_countries = MapFeatureEngine.getInstance().getAllCountryList().stream()
                .filter(p_country -> p_country.getUniqueCountryId().equals(p_countryId)).collect(Collectors.toList());
        if (!l_countries.isEmpty()) {
            return l_countries.get(0);
        } else {
            return null;
        }
    }

    public List<Country> searchByNeighbourOfCountries(Country p_country) {
        return MapFeatureEngine.getInstance().getAllCountryList().stream()
                .filter(p_l_country -> !p_l_country.equals(p_country)
                        && p_l_country.getNeighborCountriesList().contains(p_country))
                .collect(Collectors.toList());
    }

    public List<Country> searchCountryNeighborsAndNotOwned(Country p_country) throws IllegalStateException {
        return p_country.getNeighborCountriesList().stream().filter((p_l_country) -> p_l_country.getOwnedBy() == null)
                .collect(Collectors.toList());
    }
}