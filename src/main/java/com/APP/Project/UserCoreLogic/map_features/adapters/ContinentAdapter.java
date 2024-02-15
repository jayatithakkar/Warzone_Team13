package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.GameEntities.Continent;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.containers.ContinentContainer;

import java.util.*;
import java.util.stream.Collectors;

public class ContinentAdapter {
     private final MapFeatureEngine d_mapEditorEngine;
     private final ContinentContainer d_continentRepository;

    public ContinentAdapter() {
        d_mapEditorEngine = MapFeatureEngine.getInstance();
        d_continentRepository = new ContinentContainer();
    }

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

     public String remove(String p_continentName) throws EntityNotFoundException {
          Continent l_continent = d_continentRepository.findFirstByContinentName(p_continentName);
          List<Continent> l_filteredContinentList = d_mapEditorEngine.getContinentList().stream()
                    .filter(p_continent -> !p_continent.equals(l_continent)).collect(Collectors.toList());

          d_mapEditorEngine.setContinentList(l_filteredContinentList);
          return String.format("%s continent removed successfully", p_continentName);
     }
}
