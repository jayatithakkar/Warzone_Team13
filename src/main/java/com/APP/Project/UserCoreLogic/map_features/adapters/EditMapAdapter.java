package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.constants.enums.MapModelTypes;
import com.APP.Project.UserCoreLogic.constants.interfaces.StandaloneCommand;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.Container.CountryContainer;
import com.APP.Project.UserCoreLogic.Utility.FindFilePathUtil;
import com.APP.Project.UserCoreLogic.Utility.FileValidationUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The EditMapAdapter class implements the StandaloneCommand interface to handle map editing operations.
 * This class facilitates loading map files, reading continents, countries, and their neighbors, and executing map editing commands.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class EditMapAdapter implements StandaloneCommand {
     private final MapFeatureEngine d_mapEditorEngine;
     private final CountryContainer d_countryRepository;
     private final ContinentAdapter d_continentService;
     private final CountryAdapter d_countryService;
     private final CountryNeighborAdapter d_countryNeighborService;

     /**
     * Constructs an instance of EditMapAdapter.
     */
    public EditMapAdapter() {
        d_mapEditorEngine = MapFeatureEngine.getInstance();
        d_countryRepository = new CountryContainer();
        d_continentService = new ContinentAdapter();
        d_countryService = new CountryAdapter();
        d_countryNeighborService = new CountryNeighborAdapter();
    }

    /**
     * Handles the loading of a map from the specified file path.
     *
     * @param p_filePath      The file path of the map.
     * @param shouldCreateNew A boolean flag indicating whether a new map should be created if the specified file doesn't exist.
     * @return A message indicating the status of the map loading operation.
     * @throws InvalidMapException      If the map is invalid.
     * @throws AbsentTagException       If a required tag is absent.
     * @throws ResourceNotFoundException If the specified resource is not found.
     * @throws InvalidInputException    If the input is invalid.
     * @throws EntityNotFoundException  If an entity is not found.
     */
     public String handleLoadMap(String p_filePath, boolean shouldCreateNew)
               throws InvalidMapException,
               AbsentTagException,
               ResourceNotFoundException,
               InvalidInputException,
               EntityNotFoundException {

          d_mapEditorEngine.initialise();
          if (new File(p_filePath).exists()) {
               try {

                    FileValidationUtil.fetchFile(p_filePath);

                    BufferedReader l_reader = new BufferedReader(new FileReader(p_filePath));

                    String l_currentLine;
                    while ((l_currentLine = l_reader.readLine()) != null) {
                         if (l_currentLine.startsWith("[")) {

                              if (this.doLineHasModelData(l_currentLine, MapModelTypes.CONTINENT)) {
                                   readContinents(l_reader);
                              } else if (this.doLineHasModelData(l_currentLine, MapModelTypes.COUNTRY)) {

                                   readCountries(l_reader);
                              } else if (this.doLineHasModelData(l_currentLine, MapModelTypes.BORDER)) {

                                   readNeighbours(l_reader);
                              }
                         }
                    }
                    return "File loaded successfully!";
               } catch (IOException p_ioException) {
                    throw new ResourceNotFoundException("File not found!");
               }
          } else if (shouldCreateNew) {
               // Throws exception if file doesn't have required extension.
               FileValidationUtil.VerifyIfFileHasRequiredExtensionOrNot(p_filePath);

               FileValidationUtil.createFileIfNotExits(p_filePath);
               return "New file created!";
          } else {
               throw new InvalidMapException(
                         "Please check if file exists. This may happen due to error while processing.");
          }
     }

     /**
     * Handles the loading of a map from the specified file path.
     *
     * @param p_filePath The file path of the map.
     * @return A message indicating the status of the map loading operation.
     * @throws AbsentTagException       If a required tag is absent.
     * @throws InvalidMapException      If the map is invalid.
     * @throws ResourceNotFoundException If the specified resource is not found.
     * @throws InvalidInputException    If the input is invalid.
     * @throws EntityNotFoundException  If an entity is not found.
     */
     public String handleLoadMap(String p_filePath) throws AbsentTagException, InvalidMapException,
               ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
          return this.handleLoadMap(p_filePath, true);
     }

     /**
     * Reads continents from the provided BufferedReader and adds them to the map.
     *
     * @param p_reader BufferedReader containing map data
     * @throws InvalidInputException if the input is invalid
     * @throws InvalidMapException  if the map is invalid
     * @throws AbsentTagException   if a required tag is absent
     */
     private void readContinents(BufferedReader p_reader)
               throws InvalidInputException, InvalidMapException, AbsentTagException {
          String l_currentLine;
          try {
               while ((l_currentLine = p_reader.readLine()) != null && !l_currentLine.startsWith("[")) {
                    if (l_currentLine.trim().isEmpty()) {
                         continue;
                    }
                    List<String> l_continentComponentList = this.getModelComponents(l_currentLine);
                    if (l_continentComponentList.size() >= 2) {
                         d_continentService.add(l_continentComponentList.get(0), l_continentComponentList.get(1));
                    } else {
                         throw new AbsentTagException("Missing continent value!");
                    }
                    p_reader.mark(0);
               }
               p_reader.reset();
          } catch (IOException p_ioException) {
               throw new InvalidMapException("Error while processing!");
          }
     }

     /**
     * Reads countries from the provided BufferedReader and adds them to the map.
     *
     * @param p_reader BufferedReader containing map data
     * @throws EntityNotFoundException if the entity is not found
     * @throws InvalidMapException     if the map is invalid
     * @throws AbsentTagException      if a required tag is absent
     */
     private void readCountries(BufferedReader p_reader)
               throws EntityNotFoundException, InvalidMapException, AbsentTagException {
          String l_currentLine;
          try {
               while ((l_currentLine = p_reader.readLine()) != null && !l_currentLine.startsWith("[")) {
                    if (l_currentLine.trim().isEmpty()) {
                         continue;
                    }
                    List<String> l_countryComponentList = this.getModelComponents(l_currentLine);
                    if (l_countryComponentList.size() >= 3) {
                         d_countryService.add(Integer.parseInt(l_countryComponentList.get(0)),
                                   l_countryComponentList.get(1), Integer.parseInt(l_countryComponentList.get(2)));
                    } else {
                         throw new AbsentTagException("Missing country value!");
                    }
                    p_reader.mark(0);
               }
               p_reader.reset();
          } catch (IOException p_ioException) {
               throw new InvalidMapException("Error while processing!");
          }

     }

     /**
     * Reads neighbors from the provided BufferedReader and adds them to the corresponding countries.
     *
     * @param p_reader BufferedReader containing map data
     * @throws AbsentTagException if a required tag is absent
     * @throws InvalidMapException if the map is invalid
     */
     private void readNeighbours(BufferedReader p_reader) throws AbsentTagException, InvalidMapException {
          String l_currentLine;
          try {
               while ((l_currentLine = p_reader.readLine()) != null && !l_currentLine.startsWith("[")) {
                    if (l_currentLine.trim().isEmpty()) {
                         continue;
                    }
                    List<String> l_borderComponentList = this.getModelComponents(l_currentLine);
                    if (l_borderComponentList.size() > 1) {
                         Country l_country = d_countryRepository
                                   .searchByCountryId(Integer.parseInt(l_borderComponentList.get(0)));
                         if (l_country != null) {
                              for (int i = 1; i < l_borderComponentList.size(); i++) {
                                   Country l_neighbourCountry = d_countryRepository
                                             .searchByCountryId(Integer.parseInt(l_borderComponentList.get(i)));
                                   if (l_neighbourCountry != null) {
                                        d_countryNeighborService.add(l_country, l_neighbourCountry);
                                   }
                              }
                         }
                    } else {
                         throw new AbsentTagException("Missing border value!");
                    }
               }
               p_reader.mark(0);
          } catch (IOException e) {
               throw new InvalidMapException("Error while processing!");
          }
     }

     /**
     * Checks if the line contains model data of a specific type.
     *
     * @param p_currentLine   the current line of text
     * @param p_mapModelType the type of map model
     * @return true if the line has model data, otherwise false
     */
     private boolean doLineHasModelData(String p_currentLine, MapModelTypes p_mapModelType) {
          return p_currentLine.substring(p_currentLine.indexOf("[") + 1, p_currentLine.indexOf("]"))
                    .equalsIgnoreCase(p_mapModelType.getJsonValue());
     }

     /**
     * Retrieves components from a line of text representing model data.
     *
     * @param p_line the line of text
     * @return a list of model components
     */
     public List<String> getModelComponents(String p_line) {
          try {
               if (!p_line.isEmpty() && p_line.contains(" ")) {
                    List<String> l_continentComponentList = Arrays.asList(p_line.split("\\s"));
                    if (!l_continentComponentList.isEmpty()) {
                         l_continentComponentList = l_continentComponentList.stream().map(String::trim)
                                   .collect(Collectors.toList());
                         if (!(l_continentComponentList.contains(null) || l_continentComponentList.contains(""))) {
                              return l_continentComponentList;
                         }
                    }
               }
          } catch (Exception e) {
          }
          return new ArrayList<>();
     }

     /**
     * Executes the command to load a map from a file.
     *
     * @param p_commandValues the list of command values
     * @return a message indicating the result of the operation
     * @throws InvalidMapException        if the map is invalid
     * @throws ResourceNotFoundException if the resource is not found
     * @throws InvalidInputException     if the input is invalid
     * @throws AbsentTagException         if a required tag is absent
     * @throws EntityNotFoundException    if the entity is not found
     */
     @Override
     public String execute(List<String> p_commandValues)
               throws InvalidMapException,
               ResourceNotFoundException,
               InvalidInputException,
               AbsentTagException,
               EntityNotFoundException {
          if (!p_commandValues.isEmpty()) {
               // Resolve file path using absolute path of user data directory.
               String resolvedPathToFile = FindFilePathUtil.findFilePath(p_commandValues.get(0));
               return this.handleLoadMap(resolvedPathToFile);
          } else {
               throw new InvalidInputException("File name is empty!");
          }
     }
}
