package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.Container.CountryContainer;

import java.util.*;
import java.util.stream.Collectors;

public class CountryNeighborAdapter {
     private final CountryContainer d_countryRepository;

    public CountryNeighborAdapter() {
        d_countryRepository = new CountryContainer();
    }

     public String add(String p_countryName, String p_neighborCountryName) throws EntityNotFoundException {
          Country l_country = d_countryRepository.searchFirstByCountryName(p_countryName);
          Country l_neighborCountry = d_countryRepository.searchFirstByCountryName(p_neighborCountryName);
          return this.add(l_country, l_neighborCountry);
     }

     public String add(Country p_country, Country p_neighborCountry) {
          p_country.addNeighbourCountry(p_neighborCountry);
          return String.format("Neighbor %s country successfully added for %s!", p_neighborCountry.getUniqueCountryName(),
                    p_country.getUniqueCountryName());
     }

     public String remove(String p_countryName, String p_neighborCountryName) throws EntityNotFoundException {
          Country l_country = d_countryRepository.searchFirstByCountryName(p_countryName);
          Country l_neighborCountry = d_countryRepository.searchFirstByCountryName(p_neighborCountryName);

          return this.remove(l_country, l_neighborCountry);
     }

     public String remove(Country p_country, Country p_neighborCountry) {
          List<Country> l_filteredCountry = p_country.getNeighborCountriesList().stream()
                    .filter(i_p_country -> i_p_country.equals(p_neighborCountry)).collect(Collectors.toList());

          p_country.setNeighborCountriesList(l_filteredCountry);
          return String.format("Neighbor %s country successfully removed from %s!", p_neighborCountry.getUniqueCountryName(), p_country.getUniqueCountryName());
     }
}
