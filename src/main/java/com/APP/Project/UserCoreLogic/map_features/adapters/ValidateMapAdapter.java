package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.constants.interfaces.StandaloneCommand;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.GameEntities.Country;
import com.APP.Project.UserCoreLogic.GameEntities.Continent;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidMapException;
import com.APP.Project.UserCoreLogic.containers.CountryContainer;

import java.util.*;

public class ValidateMapAdapter implements StandaloneCommand {
     private final MapFeatureEngine d_mapEditorEngine;

    public ValidateMapAdapter() {
        d_mapEditorEngine = MapFeatureEngine.getInstance();
    }

     public boolean isContinentConnectedSubgraph() throws EntityNotFoundException {
          if (d_mapEditorEngine.getContinentList().size() > 1) {
               boolean l_isInvalid = false;
               String l_continentName;
               List<String> l_countriesIntoContinent;
               CountryContainer l_countryRepository = new CountryContainer();
               int l_totalContinent = d_mapEditorEngine.getContinentList().size();
               int l_compareTotalContinent = 0;
               Country l_foundCountry = null;

               Map<String, List<String>> l_continentCountryMap = d_mapEditorEngine.getContinentCountryMap();
               for (Map.Entry<String, List<String>> entry : l_continentCountryMap.entrySet()) {
                    l_continentName = entry.getKey();
                    l_countriesIntoContinent = entry.getValue();
                    int l_otherContinentNeighbour = 0;

                    for (String l_countryNameCompare : l_countriesIntoContinent) {
                         try {
                              l_foundCountry = l_countryRepository.findFirstByCountryName(l_countryNameCompare);
                         } catch (EntityNotFoundException e) {
                              e.printStackTrace();
                         }
                         if (l_foundCountry == null) {
                              continue;
                         }
                         List<Country> l_neighbourCountries = l_foundCountry.getNeighborCountriesList();

                         for (Country l_country : l_neighbourCountries) {
                              Continent l_continent = l_country.getInsideContinent();
                              String ContinentName = l_continent.getContinentName();
                              if (!(ContinentName.equals(l_continentName))) {
                                   l_otherContinentNeighbour++;
                                   break;
                              }
                         }

                         if (l_otherContinentNeighbour > 0) {
                              l_compareTotalContinent++;
                              break;
                         }
                    }
               }
               if (l_compareTotalContinent == l_totalContinent) {
                    l_isInvalid = true;
               }
               return l_isInvalid;
          } else {
               return true;
          }
     }

     public boolean isMapConnectedGraph() {
          boolean l_isValid = false;
          int l_connectedGraphCount = 0;

          List<Country> l_countryList = d_mapEditorEngine.getAllCountryList();
          for (int i = 0; i < 2; i++) {
               List<Country> l_visitedCountry = new ArrayList<>();
               Stack<Country> l_stack = new Stack<>();
               Country l_country = l_countryList.get(i);
               l_stack.push(l_country);

               while (!l_stack.isEmpty()) {
                    Country l_countryGet = l_stack.pop();
                    l_visitedCountry.add(l_countryGet);
                    List<Country> l_neighbourCountries = l_countryGet.getNeighborCountriesList();
                    for (Country l_pushCountry : l_neighbourCountries) {
                         if (!l_stack.contains(l_pushCountry)) {
                              int l_counter = 0;
                              for (Country l_compareCountry : l_visitedCountry) {
                                   if (l_pushCountry.equals(l_compareCountry)) {
                                        l_counter++;
                                   }
                              }
                              if (l_counter == 0) {
                                   l_stack.push(l_pushCountry);
                              }
                         }
                    }
               }
               int compareCounter = 0;
               for (Country l_compareCountry : l_countryList) {
                    for (Country l_compare2 : l_visitedCountry) {
                         if (l_compare2.equals(l_compareCountry)) {
                              compareCounter++;
                         }
                    }
               }
               if (compareCounter == l_countryList.size()) {
                    ++l_connectedGraphCount;
               }
          }
          if (l_connectedGraphCount == 2) {
               l_isValid = true;
          }
          return l_isValid;
     }

     private boolean validationControlValue(List<Continent> p_continentList) {
          boolean l_isValid = true;

          for (Continent l_continent : p_continentList) {
               if (l_continent.getContinentControlValue() < 0) {
                    l_isValid = false;
                    break;
               }
          }
          return l_isValid;
     }

     @Override
     public String execute(List<String> p_commandValues) throws InvalidMapException, EntityNotFoundException {
          // Checks map has atleast 1 continent
          if (d_mapEditorEngine.getContinentList().size() > 0) {
               if (validationControlValue(d_mapEditorEngine.getContinentList())) {
                    if (d_mapEditorEngine.getAllCountryList().size() > 1) {
                         if (d_mapEditorEngine.getAllCountryList().size() >= d_mapEditorEngine.getContinentList().size()) {
                              if (isContinentConnectedSubgraph()) {
                                   if (isMapConnectedGraph()) {
                                        return "Map validation passed successfully!";
                                   } else {
                                        throw new InvalidMapException("map must be a connected graph!");
                                   }
                              } else {
                                   throw new InvalidMapException("Continent must be a connected sub-graph!");
                              }
                         } else {
                              throw new InvalidMapException(
                                        "Total continents must be lesser or equal to the countries!");
                         }
                    } else {
                         throw new InvalidMapException("At least one country required!");
                    }
               } else {
                    throw new InvalidMapException("ControlValue is not valid!");
               }
          } else {
               throw new InvalidMapException("At least one continent required!");
          }
     }
}
