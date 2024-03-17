package com.APP.Project.UserCoreLogic.Container;

import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for searching country in different way like by it's
 * name, id and neighbour
 *
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
public class CountryContainer {
    /**
     * Retrieves a list of countries that exactly match the specified country name.
     * It's recommended to format the input name properly to ensure accurate
     * matching.
     *
     * @param p_countryName The name of the country to search for. This parameter
     *                    should not be null.
     * @return A List containing the matched countries. The list is empty if no
     *         countries match the provided name.
     */
    public List<Country> findByCountryName(String p_countryName) {
        return MapFeatureEngine.getInstance().getCountryList().stream()
                .filter(p_country -> p_country.getCountryName().equals(p_countryName)).collect(Collectors.toList());
    }

    /**
     * Retrieves the first country that matches the specified country name
     *
     * @param p_countryName The name of the country to search for. This parameter
     *                    should not be null.
     * @return The first Country object that matches the given country name.
     * @throws EntityNotFoundException If no country with the given name is found.
     */
    public Country findFirstByCountryName(String p_countryName) throws EntityNotFoundException {
        List<Country> l_countryList = this.findByCountryName(p_countryName);
        if (l_countryList.size() > 0)
            return l_countryList.get(0);
        throw new EntityNotFoundException(String.format("'%s' country not found", p_countryName));
    }

    /**
     * Finds and returns a country by its unique ID.
     * This method searches for a country with the specified ID. If a country with
     * the given ID is found, it is returned.
     * Otherwise, this method returns null, indicating that no country with the
     * specified ID could be found.
     *
     * @param p_countryId The unique identifier of the country to search for. This
     *                  parameter should not be null.
     * @return The Country object with the specified ID, or null if no such country
     *         is found.
     */
    public Country findByCountryId(Integer p_countryId) {
        List<Country> l_countries = MapFeatureEngine.getInstance().getCountryList().stream()
                .filter(p_country -> p_country.getCountryId().equals(p_countryId)).collect(Collectors.toList());
        if (!l_countries.isEmpty()) {
            return l_countries.get(0);
        } else {
            return null;
        }
    }

    /**
     * Finds all countries that are neighbors of the specified country.
     * This method searches through the list of available countries, identifying
     * those that have the specified country
     * as a neighbor. The specified country itself is not included in the results.
     * This method assumes that the
     * relationship of being neighbors is mutual; that is, if country A is a
     * neighbor of country B, then country B is
     * also considered a neighbor of country A.
     *
     * @param p_country The country for which to find neighbors. This parameter should
     *                not be null.
     * @return A List of countries that are neighbors of the specified country. The
     *         list is empty if the country has
     *         no neighbors or if the specified country is not found.
     */
    public List<Country> findByNeighbourOfCountries(Country p_country) {
        return MapFeatureEngine.getInstance().getCountryList().stream()
                .filter(p_l_country -> !p_l_country.equals(p_country)
                        && p_l_country.getNeighbourCountries().contains(p_country))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of neighboring countries of the specified country that are
     * not owned by any entity.
     * This method filters through the neighboring countries of the provided country
     * and selects those which are
     * currently not owned (i.e., their 'ownedBy' attribute is null). It is useful
     * for identifying potential
     * countries for expansion or diplomatic relations that are not currently under
     * the control of another entity.
     *
     * @param p_country The country whose neighbors are to be evaluated. This
     *                parameter should not be null.
     * @return A List of Country objects representing the neighboring countries that
     *         are not owned.
     * @throws IllegalStateException If the provided country is null, indicating an
     *                               inappropriate use of the method.
     */
    public List<Country> findCountryNeighborsAndNotOwned(Country p_country) throws IllegalStateException {
        return p_country.getNeighbourCountries().stream().filter((p_l_country) -> p_l_country.getOwnedBy() == null)
                .collect(Collectors.toList());
    }
}