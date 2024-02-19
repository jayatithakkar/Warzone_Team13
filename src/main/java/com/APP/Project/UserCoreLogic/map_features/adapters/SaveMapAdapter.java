package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.constants.interfaces.StandaloneCommand;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidMapException;
import com.APP.Project.UserCoreLogic.exceptions.ResourceNotFoundException;
import com.APP.Project.UserCoreLogic.Utility.FileValidationUtil;
import com.APP.Project.UserCoreLogic.Utility.FindFilePathUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SaveMapAdapter class implements the StandaloneCommand interface to provide functionality
 * for saving map data to a file.
 * 
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 * 
 */
public class SaveMapAdapter implements StandaloneCommand{
     private final MapFeatureEngine d_mapEditorEngine;

     /**
     * Constructor for SaveMapAdapter class.
     * Initializes the MapFeatureEngine instance.
     */
     public SaveMapAdapter() {
          d_mapEditorEngine = MapFeatureEngine.getInstance();
     }

     /**
     * Saves map data to the specified file.
     * 
     * @param p_fileObject The file object to save the map data to.
     * @return A string indicating the status of the file saving operation.
     * @throws InvalidInputException If an error occurs due to invalid input.
     */
     public String saveToFile(File p_fileObject) throws InvalidInputException {
          try (Writer l_writer = new FileWriter(p_fileObject)) {
               l_writer.write("[" + "Continents" + "]\n");

               for (Continent continents : d_mapEditorEngine.getContinentList()) {
                    l_writer.write(continents.getContinentName() + " " + continents.getContinentControlValue() + "\n");
               }

               l_writer.write("\n[" + "Countries" + "]\n");

               for (Country country : d_mapEditorEngine.getAllCountryList()) {
                    l_writer.write(country.getUniqueCountryId() + " " + country.getUniqueCountryName() + " "
                              + country.getInsideContinent().getUniqueContinentId() + "\n");
               }

               l_writer.write("\n[" + "borders" + "]\n");

               for (Map.Entry<Integer, Set<Integer>> entry : d_mapEditorEngine.getCountryNeighbourMap().entrySet()) {
                    int key = entry.getKey();
                    Set<Integer> neighbour = entry.getValue();
                    l_writer.write(key + " ");
                    for (Integer a : neighbour) {
                         l_writer.write(a + " ");
                    }
                    l_writer.write("\n");
               }
               d_mapEditorEngine.initialise();
               return "File saved successfully";
          } catch (IOException p_ioException) {
               throw new InvalidInputException("Error while saving the file!");
          }
     }

     /**
     * Executes the SaveMapAdapter command.
     * Validates the map using ValidateMapAdapter and saves it to the specified file.
     * 
     * @param p_commandValues List of command values.
     * @return A string indicating the status of the execution.
     * @throws InvalidInputException If an error occurs due to invalid input.
     * @throws InvalidMapException If the map is invalid.
     * @throws ResourceNotFoundException If a resource is not found.
     * @throws EntityNotFoundException If an entity is not found.
     */
     @Override
     public String execute(List<String> p_commandValues) throws InvalidInputException, InvalidMapException, ResourceNotFoundException, EntityNotFoundException {

          ValidateMapAdapter l_validateObj = new ValidateMapAdapter();
          l_validateObj.execute(null);

          return saveToFile(FileValidationUtil.fetchFile(FindFilePathUtil.findFilePath(p_commandValues.get(0))));
     }
}
