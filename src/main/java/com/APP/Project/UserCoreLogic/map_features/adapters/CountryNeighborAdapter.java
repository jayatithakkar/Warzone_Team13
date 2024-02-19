package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.Container.CountryContainer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CountryNeighborAdapter class provides methods to add and remove neighbor countries for a given country.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class CountryNeighborAdapter {
     private final CountryContainer d_countryRepository;

     /**
     * Constructs a new CountryNeighborAdapter object.
     */
    public CountryNeighborAdapter() {
        d_countryRepository = new CountryContainer();
    }

    /**
     * Adds a neighbor country to the specified country.
     *
     * @param p_countryName        The name of the country to which the neighbor country will be added.
     * @param p_neighborCountryName The name of the neighbor country to be added.
     * @return A message indicating the success of the operation.
     * @throws EntityNotFoundException If either the country or the neighbor country is not found.
     */
     public String add(String p_countryName, String p_neighborCountryName) throws EntityNotFoundException {
          Country l_country = d_countryRepository.searchFirstByCountryName(p_countryName);
          Country l_neighborCountry = d_countryRepository.searchFirstByCountryName(p_neighborCountryName);
          return this.add(l_country, l_neighborCountry);
     }

     /**
     * Adds a neighbor country to the specified country.
     *
     * @param p_country        The country to which the neighbor country will be added.
     * @param p_neighborCountry The neighbor country to be added.
     * @return A message indicating the success of the operation.
     */
     public String add(Country p_country, Country p_neighborCountry) {
          p_country.addNeighbourCountry(p_neighborCountry);
          return String.format("Neighbor %s country successfully added for %s!", p_neighborCountry.getUniqueCountryName(),
                    p_country.getUniqueCountryName());
     }

     /**
     * Removes a neighbor country from the specified country.
     *
     * @param p_countryName        The name of the country from which the neighbor country will be removed.
     * @param p_neighborCountryName The name of the neighbor country to be removed.
     * @return A message indicating the success of the operation.
     * @throws EntityNotFoundException If either the country or the neighbor country is not found.
     */
     public String remove(String p_countryName, String p_neighborCountryName) throws EntityNotFoundException {
          Country l_country = d_countryRepository.searchFirstByCountryName(p_countryName);
          Country l_neighborCountry = d_countryRepository.searchFirstByCountryName(p_neighborCountryName);

          return this.remove(l_country, l_neighborCountry);
     }

     /**
     * Removes a neighbor country from the specified country.
     *
     * @param p_country        The country from which the neighbor country will be removed.
     * @param p_neighborCountry The neighbor country to be removed.
     * @return A message indicating the success of the operation.
     */
     public String remove(Country p_country, Country p_neighborCountry) {
          List<Country> l_filteredCountry = p_country.getNeighborCountriesList().stream()
                    .filter(i_p_country -> i_p_country.equals(p_neighborCountry)).collect(Collectors.toList());

          p_country.setNeighborCountriesList(l_filteredCountry);
          return String.format("Neighbor %s country successfully removed from %s!", p_neighborCountry.getUniqueCountryName(), p_country.getUniqueCountryName());
     }
}
