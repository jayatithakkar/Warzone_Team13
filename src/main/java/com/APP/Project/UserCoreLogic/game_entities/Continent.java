package com.APP.Project.UserCoreLogic.game_entities;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is for getter and setter methods specific to Continent GameEntities
 *
 * @author Sushant Sinha
 */
public class Continent {
    /**
     * Auto-generated unique ID of the continent.
     */
    private Integer d_uniqueContinentId;
    private String d_uniqueContinentName;
    private Integer d_continentControlValue;
    private ArrayList<Country> d_allCountryList;

    /**
     * Used for generating unique IDs for the each continent. Counter is incremented every time
     */
    public static int d_counter = 0;

    /**
     * Assigns Continent iD to the continent and creates the initializes the ArrayList.
     */
    public Continent() {
        d_allCountryList = new ArrayList<>();
        this.d_uniqueContinentId = ++d_counter;
    }

    /**
     * Get the unique id of the specific continent using the index of the location
     *
     * @return Unique value for the continent ID.
     */
    public Integer getUniqueContinentId() {
        return d_uniqueContinentId;
    }

    /**
     * Set the value of continent name.
     *
     * @param p_uniqueContinentName Name of the continent.
     */
    public void setContinentName(String p_uniqueContinentName) {
        d_uniqueContinentName = p_uniqueContinentName;
    }

    /**
     * Get the continent name.
     *
     * @return (Unique) Continent name.
     */
    public String getContinentName() {
        return d_uniqueContinentName;
    }

    /**
     * Set the continent's control value.
     *
     * @param p_continentControlValue Value of the continent control.
     */
    public void setContinentControlValue(int p_continentControlValue) {
        d_continentControlValue = p_continentControlValue;
    }

    /**
     * Get the continent control value.
     *
     * @return the continent control value.
     */
    public int getContinentControlValue() {
        return d_continentControlValue;
    }

    /**
     * Get the list of all the countries present in this Continent.
     *
     * @return ArrayList of the of countries
     */
    public ArrayList<Country> getAllCountryList() {
        return d_allCountryList;
    }

    /**
     * Set the country list for this continent.
     *
     * @param p_allCountryList Value of the list.
     */
    public void setCountryList(ArrayList<Country> p_allCountryList) {
        d_allCountryList = p_allCountryList;
    }

    /**
     * Add the country to this continent.
     *
     * @param p_country Value of the country to be added to this continent.
     */
    public void addCountry(Country p_country) {
        // ArrayList allows duplicates
        // Set will not have any duplicate elements.
        d_allCountryList.add(p_country);
    }

    /**
     * Remove country from this continent.
     *
     * @param p_country Value of the country to be removed from this continent.
     */
    public void removeCountry(Country p_country) {
        // ArrayList allows duplicates
        // Set will not have any duplicate elements.
        d_allCountryList.remove(p_country);
    }

    /**
     * Resets the counter to 0 (zero).
     * Needed when the engine resets
     */
    public static void resetCounter() {
        d_counter = 0;
    }

    /**
     * Checking/Verifying that the same continent object is being referred using the continent ID
     *
     * @param l_p_o Value of the second continent to be compared with.
     * @return True if the both are same.
     */
    @Override
    public boolean equals(Object l_p_o) {
        if (this == l_p_o) return true;
        if (l_p_o == null || getClass() != l_p_o.getClass()) return false;
        Continent l_that = (Continent) l_p_o;
        return d_uniqueContinentId.equals(l_that.d_uniqueContinentId);
    }

    /**
     * Return the hash value of the continent.
     *
     * @return Hash value of the continent.
     */
    @Override
    public int hashCode() {
        return Objects.hash(d_uniqueContinentId);
    }
}
