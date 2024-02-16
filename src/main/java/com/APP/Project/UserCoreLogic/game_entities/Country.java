package com.APP.Project.UserCoreLogic.game_entities;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

/**
 * This class is to set and get the country variables
 *
 * @author Sushant Sinha
 */
public class Country {
    /**
     * Auto-generated ID of the country.
     */
    private Integer d_uniqueCountryId;
    private String d_uniqueCountryName;
    private Continent d_insideContinent;
    private List<Country> d_neighborCountriesList;
    private Player d_ownedByPlayer;
    private int d_numberOfArmies;

    /**
     * Used for generating unique IDs for each country.
     */
    public static int d_counter = 0;

    /**
     * Assigns country id using counter to the country and initializes the neighbor countries ArrayList.
     */
    public Country() {
        this.d_uniqueCountryId = ++d_counter;
        d_neighborCountriesList = new ArrayList<>();
    }

    /**
     * Assigns country id using the argument passed and initializes the neighbour countries ArrayList.
     *
     * @param p_uniqueCountryId
     */
    public Country(int p_uniqueCountryId) {
        this.d_uniqueCountryId = p_uniqueCountryId;
        d_neighborCountriesList = new ArrayList<>();
    }

    /**
     * Get the ID for the country.
     *
     * @return the country ID.
     */
    public Integer getUniqueCountryId() {
        return d_uniqueCountryId;
    }


    /**
     * Set the country name.
     *
     * @param p_uniqueCountryName Value of the country name.
     */
    public void setCountryName(String p_uniqueCountryName) {
        d_uniqueCountryName = p_uniqueCountryName;
    }

    /**
     * Get the country name.
     *
     * @return this name of the country.
     */
    public String getUniqueCountryName() {
        return d_uniqueCountryName;
    }

    /**
     * Set the continent inside which this country is.
     *
     * @param p_insideContinent Represents the value of continent.
     */
    public void setInsideContinent(Continent p_insideContinent) {
        d_insideContinent = p_insideContinent;
    }


    /**
     * Get the continent inside which this country is.
     *
     * @return continent of this country.
     */
    public Continent getInsideContinent() {
        return d_insideContinent;
    }

    /**
     * Set the list of the neighboring countries.
     *
     * @param p_neighborCountriesList List of neighboring countries.
     */
    public void setNeighborCountriesList(List<Country> p_neighborCountriesList) {
        d_neighborCountriesList = p_neighborCountriesList;
    }

    /**
     * Get the list of neighboring countries.
     *
     * @return Value of neighboring countries list.
     */
    public List<Country> getNeighborCountriesList() {
        return d_neighborCountriesList;
    }

    /**
     * Add the neighbor to the country.
     *
     * @param p_neighborCountry Value of the neighbor country.
     */
    public void addNeighbourCountry(Country p_neighborCountry) {
        d_neighborCountriesList.add(p_neighborCountry);
    }

    /**
     * Remove the neighbor from the country's neighboring list.
     *
     * @param p_neighborCountry Value of the neighbor country.
     */
    public void removeNeighborCountry(Country p_neighborCountry) {
        d_neighborCountriesList.remove(p_neighborCountry);
    }

    /**
     * Reset the counter to 0 (zero). 
     * Needed when the engine resets.
     */
    public static void resetCounter() {
        d_counter = 0;
    }

    /**
     * Get Player which owns the country.
     *
     * @return Player.
     */
    public Player getOwnedBy() {
        return d_ownedByPlayer;
    }

    /**
     * Set Player which owns the country.
     *
     * @param p_ownedByPlayer Country owner object.
     */
    public void setOwnedBy(Player p_ownedByPlayer) {
        d_ownedByPlayer = p_ownedByPlayer;
    }

    /**
     * Get the number of armies that are placed on this country by the Player
     *
     * @return Value of the count of armies.
     */
    public int getNumberOfArmies() {
        return d_numberOfArmies;
    }

    /**
     * Set the number of armies for this country placed by the Player.
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
        return d_uniqueCountryId.equals(l_l_country.d_uniqueCountryId) &&
                d_insideContinent.equals(l_l_country.d_insideContinent);
    }

    /**
     * Return the hash value of the country.
     *
     * @return Hash value of the country.
     */
    @Override
    public int hashCode() {
        return Objects.hash(d_uniqueCountryId, d_insideContinent);
    }
}
