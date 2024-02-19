package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.Container.ContinentContainer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The ContinentAdapter class facilitates the interaction between the MapFeatureEngine
 * and the ContinentContainer to manage continents in the map editor.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 * 
 */
public class ContinentAdapter {
     private final MapFeatureEngine d_mapEditorEngine;
     private final ContinentContainer d_continentRepository;

     /**
     * Constructs a new ContinentAdapter object.
     */
    public ContinentAdapter() {
        d_mapEditorEngine = MapFeatureEngine.getInstance();
        d_continentRepository = new ContinentContainer();
    }

    /**
     * Adds a continent with the specified name and control value.
     *
     * @param p_continentName the name of the continent to add
     * @param p_countryValue  the control value of the continent
     * @return a message indicating the success of the operation
     * @throws InvalidInputException if an invalid format is provided for the continent control value
     */
     public String add(String p_continentName, String p_countryValue) throws InvalidInputException {
          try {
               int l_parsedControlValue = Integer.parseInt(p_countryValue);
               Continent l_continent = new Continent();
               l_continent.setContinentName(p_continentName);
               l_continent.setContinentControlValue(l_parsedControlValue);
               d_mapEditorEngine.addContinent(l_continent);
               return String.format("%s continent added successfully", p_continentName);
          } catch (Exception e) {
               throw new InvalidInputException("Invalid format provided for Continent control value");
          }
     }

     /**
     * Removes a continent with the specified name.
     *
     * @param p_continentName the name of the continent to remove
     * @return a message indicating the success of the operation
     * @throws EntityNotFoundException if the continent with the specified name is not found
     */
     public String remove(String p_continentName) throws EntityNotFoundException {
          Continent l_continent = d_continentRepository.searchFirstByContinentName(p_continentName);
          List<Continent> l_filteredContinentList = d_mapEditorEngine.getContinentList().stream()
                    .filter(p_continent -> !p_continent.equals(l_continent)).collect(Collectors.toList());

          d_mapEditorEngine.setContinentList(l_filteredContinentList);
          return String.format("%s continent removed successfully", p_continentName);
     }
}
