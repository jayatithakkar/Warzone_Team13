package com.APP.Project.UserCoreLogic.game_entities;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class provides the getter-setter methods for different operation on the Continent entity.
 *
 * @author Sushant Sinha
 */
public class Continent {
    /**
     * Generated the id for different continents.
     */
    private Integer d_continentId;
    private String d_continentName;
    private Integer d_continentControlValue;
    private String d_continentColor;
    private ArrayList<Country> d_countryList;

    /**
     * Needed for tracking the used IDs for the continent.
     */
    public static int d_SerialNumber = 0;

    /**
     * Assigns the ID to the continent. Creates the countries list.
     */
    public Continent() {
        this.d_continentId = ++d_SerialNumber;
        d_countryList = new ArrayList<>();
    }

    /**
     * Gets the ID of the continent.
     *
     * @return Value of the continent ID.
     */
    public Integer getContinentId() {
        return d_continentId;
    }

    /**
     * Sets the continent name.
     *
     * @param p_continentName Name of the continent.
     */
    public void setContinentName(String p_continentName) {
        d_continentName = p_continentName;
    }

    /**
     * Gets the continent name.
     *
     * @return continent name.
     */
    public String getContinentName() {
        return d_continentName;
    }

    /**
     * Sets the continent control value.
     *
     * @param p_continentControlValue Value of the continent control.
     */
    public void setContinentControlValue(int p_continentControlValue) {
        d_continentControlValue = p_continentControlValue;
    }

    /**
     * Gets the continent control value.
     *
     * @return Value of the continent control.
     */
    public int getContinentControlValue() {
        return d_continentControlValue;
    }

    /**
     * Gets the list of countries present in this continent.
     *
     * @return Value of the list of countries
     */
    public ArrayList<Country> getCountryList() {
        return d_countryList;
    }

    /**
     * Sets the list of countries present in this continent.
     *
     * @param p_countryList Value of the list.
     */
    public void setCountryList(ArrayList<Country> p_countryList) {
        d_countryList = p_countryList;
    }

    /**
     * Adds a country to the list of countries present in this continent.
     *
     * @param p_country Value of the country to be added.
     */
    public void addCountry(Country p_country) {
        d_countryList.add(p_country);
    }

    /**
     * Removes the country from this continent.
     *
     * @param p_country Value of the country to be removed.
     */
    public void removeCountry(Country p_country) {
        // Set will not have any duplicate elements.
        d_countryList.remove(p_country);
    }

    /**
     * Resets the serial number to zero. Needed when the map engine is being reset.
     */
    public static void resetSerialNumber() {
        d_SerialNumber = 0;
    }

    /**
     * Checks if both objects are the same using continent ID of the object.
     *
     * @param l_p_o Value of the second element to be checked with.
     * @return True if the both are same.
     */
    @Override
    public boolean equals(Object l_p_o) {
        if (this == l_p_o) return true;
        if (l_p_o == null || getClass() != l_p_o.getClass()) return false;
        Continent l_that = (Continent) l_p_o;
        return d_continentId.equals(l_that.d_continentId);
    }

    /**
     * Returns the hash value of the continent.
     *
     * @return Hash value of the continent.
     */
    @Override
    public int hashCode() {
        return Objects.hash(d_continentId);
    }
}
