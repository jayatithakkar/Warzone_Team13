package com.APP.Project.UserCoreLogic.game_entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class provides the getter and setter methods for the Country entity
 *
 * @author Sushant Sinha
 */
public class Country {
    /**
     * Generate ID of the country.
     */
    private final Integer COUNTRY_ID;
    private String d_countryName;
    private Continent d_continent;
    private List<Country> d_neighbourCountries;
    private Player d_ownedBy;
    private int d_numberOfArmies;

    /**
     * Needed for tracking the used IDs for the Country.
     */
    public static int d_SerialNumber = 0;

    /**
     * Assigns country id to the country and creates a list of neighbouring countries.
     */
    public Country() {
        this.COUNTRY_ID = ++d_SerialNumber;
        d_neighbourCountries = new ArrayList<>();
    }

    /**
     * Assigns country id to the country and creates a list of neighbouring countries.
     *
     * @param p_countryId Country id.
     */
    public Country(int p_countryId) {
        this.COUNTRY_ID = p_countryId;
        d_neighbourCountries = new ArrayList<>();
    }

    /**
     * Sets the ID for the country.
     *
     * @return Value of the country ID.
     */
    public Integer getCountryId() {
        return COUNTRY_ID;
    }


    /**
     * Sets the name for the country.
     *
     * @param p_countryName Value of the country name.
     */
    public void setCountryName(String p_countryName) {
        d_countryName = p_countryName;
    }

    /**
     * Gets the name of this country.
     *
     * @return this name of the country.
     */
    public String getCountryName() {
        return d_countryName;
    }

    /**
     * Sets the continent inside which this country is.
     *
     * @param p_continent Represents the value of continent.
     */
    public void setContinent(Continent p_continent) {
        d_continent = p_continent;
    }


    /**
     * Gets the continent inside which this is country is.
     *
     * @return continent of this country.
     */
    public Continent getContinent() {
        return d_continent;
    }

    /**
     * Sets the list of the neighboring countries.
     *
     * @param p_neighbourCountries List of neighboring countries.
     */
    public void setNeighbourCountries(List<Country> p_neighbourCountries) {
        d_neighbourCountries = p_neighbourCountries;
    }

    /**
     * Gets the list of neighboring countries.
     *
     * @return Value of neighboring countries list.
     */
    public List<Country> getNeighbourCountries() {
        return d_neighbourCountries;
    }

    /**
     * Adds a neighbor to the country.
     *
     * @param p_neighbourCountry Value of the neighbor country.
     */
    public void addNeighbourCountry(Country p_neighbourCountry) {
        d_neighbourCountries.add(p_neighbourCountry);
    }

    /**
     * Removes a neighbor from the country.
     *
     * @param p_neighbourCountry Value of the neighbor country.
     */
    public void removeNeighbourCountry(Country p_neighbourCountry) {
        d_neighbourCountries.remove(p_neighbourCountry);
    }

    /**
     * Resets the serial number to zero. Needed when the map engine is being reset.
     */
    public static void resetSerialNumber() {
        d_SerialNumber = 0;
    }

    /**
     * Getter method to determine who owns this country.
     *
     * @return country owner object.
     */
    public Player getOwnedBy() {
        return d_ownedBy;
    }

    /**
     * Setter method for country owner.
     *
     * @param p_ownedBy Country owner object.
     */
    public void setOwnedBy(Player p_ownedBy) {
        d_ownedBy = p_ownedBy;
    }

    /**
     * Gets the number of armies that are placed on this country by the player <code>getOwnedBy</code>
     *
     * @return Value of the count of armies.
     */
    public int getNumberOfArmies() {
        return d_numberOfArmies;
    }

    /**
     * Sets the number of armies for this country placed by the player.
     *
     * @param p_numberOfArmies Values of the count of armies.
     */
    public void setNumberOfArmies(int p_numberOfArmies) {
        d_numberOfArmies = p_numberOfArmies;
    }

    /**
     * Checks if both objects are the same using both the country and continent of the object.
     *
     * @param p_l_o Value of the second element to be checked with.
     * @return True if the both are same.
     */
    @Override
    public boolean equals(Object p_l_o) {
        if (this == p_l_o) return true;
        if (p_l_o == null || getClass() != p_l_o.getClass()) return false;
        Country l_l_country = (Country) p_l_o;
        return COUNTRY_ID.equals(l_l_country.COUNTRY_ID) &&
                d_continent.equals(l_l_country.d_continent);
    }

    /**
     * Return the hash value of the country.
     *
     * @return Hash value of the country.
     */
    @Override
    public int hashCode() {
        return Objects.hash(COUNTRY_ID, d_continent);
    }
}
