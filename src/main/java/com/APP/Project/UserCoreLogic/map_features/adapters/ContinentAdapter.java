package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.logger.LogEntryBuffer;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.Container.ContinentContainer;

import java.util.List;
import java.util.stream.Collectors;


/**
 * ContinentAdapter class provides methods to interact with continents in the map editor.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class ContinentAdapter {
    
    private final MapFeatureEngine d_mapEditorEngine;
    private final ContinentContainer d_continentRepository;
    private final LogEntryBuffer d_logEntryBuffer;

   /**
     * Constructs a ContinentAdapter object initializing required dependencies.
     */
    public ContinentAdapter() {
        d_mapEditorEngine = MapFeatureEngine.getInstance();
        d_continentRepository = new ContinentContainer();
        d_logEntryBuffer = LogEntryBuffer.getLogger();
    }

    /**
     * Adds a new continent to the map.
     *
     * @param p_continentName the name of the continent to add
     * @param p_countryValue  the control value of the continent
     * @return a message indicating the success of the operation
     * @throws InvalidInputException if the continent control value is not in valid format
     */
    public String add(String p_continentName, String p_countryValue) throws InvalidInputException {
        try {
            int l_parsedControlValue = Integer.parseInt(p_countryValue);
            Continent l_continent = new Continent();
            l_continent.setContinentName(p_continentName);
            l_continent.setContinentControlValue(l_parsedControlValue);
            d_mapEditorEngine.addContinent(l_continent);
            if (!d_mapEditorEngine.getLoadingMap()) {
                d_logEntryBuffer.dataChanged("editcontinent", "\n---EDITCONTINENT---\n" + l_continent.getContinentName() + " is added to the list!\n");
            }
            return String.format("%s continent added!", p_continentName);
        } catch (Exception e) {
            throw new InvalidInputException("Continent control value is not in valid format!");
        }
    }

    /**
     * Removes a continent from the map.
     *
     * @param p_continentName the name of the continent to remove
     * @return a message indicating the success of the operation
     * @throws EntityNotFoundException if the continent to remove is not found
     */
    public String remove(String p_continentName) throws EntityNotFoundException {
        Continent l_continent = d_continentRepository.findFirstByContinentName(p_continentName);
        List<Continent> l_filteredContinentList = d_mapEditorEngine.getContinentList().stream()
                .filter(p_continent -> !p_continent.equals(l_continent)
                ).collect(Collectors.toList());
        d_mapEditorEngine.setContinentList(l_filteredContinentList);
        if (!d_mapEditorEngine.getLoadingMap()) {
            d_logEntryBuffer.dataChanged("editcontinent", "\n---EDITCONTINENT---\n" + l_continent.getContinentName() + " is removed to the list!\n");
        }
        return String.format("%s continent removed!", p_continentName);
    }
}
