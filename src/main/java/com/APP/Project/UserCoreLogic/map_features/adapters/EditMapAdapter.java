package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.constants.enums.MapModelTypes;
import com.APP.Project.UserCoreLogic.constants.interfaces.StandaloneCommand;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.logger.LogEntryBuffer;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.Container.ContinentContainer;
import com.APP.Project.UserCoreLogic.Container.CountryContainer;
import com.APP.Project.UserCoreLogic.Utility.FileValidationUtil;
import com.APP.Project.UserCoreLogic.Utility.FindFilePathUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * EditMapAdapter class represents an adapter for editing maps. It implements the StandaloneCommand interface.
 * This class provides methods to read map data, including continent, country, and their neighbors,
 * from a file and update the map accordingly.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class EditMapAdapter implements StandaloneCommand {
    
    private final MapFeatureEngine d_mapEditorEngine;

    private final ContinentContainer d_continentRepository;
    private final CountryContainer d_countryRepository;
    private final ContinentAdapter d_continentService;
    private final CountryAdapter d_countryService;
    private final CountryNeighborAdapter d_countryNeighborService;
    private final LogEntryBuffer d_logEntryBuffer;

    /**
     * Constructs an EditMapAdapter object.
     */
    public EditMapAdapter() {
        d_mapEditorEngine = MapFeatureEngine.getInstance();
        d_continentRepository = new ContinentContainer();
        d_countryRepository = new CountryContainer();
        d_continentService = new ContinentAdapter();
        d_countryService = new CountryAdapter();
        d_countryNeighborService = new CountryNeighborAdapter();
        d_logEntryBuffer = LogEntryBuffer.getLogger();
    }

    /**
     * Handles the loading of a map from the specified file path.
     *
     * @param p_filePath      The path of the file to load.
     * @param shouldCreateNew True if a new file should be created if not found, false otherwise.
     * @return A message indicating the status of the operation.
     * @throws InvalidMapException       If the map is invalid.
     * @throws AbsentTagException       If required tags are absent.
     * @throws ResourceNotFoundException If the specified file is not found.
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
        d_mapEditorEngine.setLoadingMap(true);
        if (new File(p_filePath).exists()) {
            try {
                FileValidationUtil.retrieveFile(p_filePath);
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
            FileValidationUtil.checksIfFileHasRequiredExtension(p_filePath);

            FileValidationUtil.createFileIfNotExists(p_filePath);
            return "New file created!";
        } else {
            throw new InvalidMapException("Please check if file exists. This may happen due to error while processing.");
        }
    }

    /**
     * Handles the loading of a map from the specified file path.
     *
     * @param p_filePath The path of the file to load.
     * @return A message indicating the status of the operation.
     * @throws AbsentTagException       If required tags are absent.
     * @throws InvalidMapException     If the map is invalid.
     * @throws ResourceNotFoundException If the specified file is not found.
     * @throws InvalidInputException  If the input is invalid.
     * @throws EntityNotFoundException If an entity is not found.
     */
    public String handleLoadMap(String p_filePath) throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
        return this.handleLoadMap(p_filePath, true);
    }

    /**
     * Reads continent data from the provided BufferedReader.
     * 
     * @param p_reader The BufferedReader containing the map data.
     * @throws InvalidInputException If the input data is invalid.
     * @throws InvalidMapException  If the map data is invalid.
     * @throws AbsentTagException   If a required tag is missing in the map data.
     */
    private void readContinents(BufferedReader p_reader) throws InvalidInputException, InvalidMapException, AbsentTagException {
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
     * Reads country data from the provided BufferedReader.
     * 
     * @param p_reader The BufferedReader containing the map data.
     * @throws EntityNotFoundException If a required entity is not found in the repository.
     * @throws InvalidMapException     If the map data is invalid.
     * @throws AbsentTagException      If a required tag is missing in the map data.
     */
    private void readCountries(BufferedReader p_reader) throws EntityNotFoundException, InvalidMapException, AbsentTagException {
        String l_currentLine;
        try {
            while ((l_currentLine = p_reader.readLine()) != null && !l_currentLine.startsWith("[")) {
                if (l_currentLine.trim().isEmpty()) {
                    continue;
                }
                List<String> l_countryComponentList = this.getModelComponents(l_currentLine);
                if (l_countryComponentList.size() >= 3) {
                    d_countryService.add(Integer.parseInt(l_countryComponentList.get(0)), l_countryComponentList.get(1), Integer.parseInt(l_countryComponentList.get(2)));
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
     * Reads neighboring countries from the provided BufferedReader and updates the country neighbor relations.
     *
     * @param p_reader BufferedReader containing map data
     * @throws AbsentTagException if a required tag is missing in the map data
     * @throws InvalidMapException if an error occurs while processing the map data
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
                    Country l_country = d_countryRepository.findByCountryId(Integer.parseInt(l_borderComponentList.get(0)));
                    if (l_country != null) {
                        for (int i = 1; i < l_borderComponentList.size(); i++) {
                            Country l_neighbourCountry = d_countryRepository.findByCountryId(Integer.parseInt(l_borderComponentList.get(i)));
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
     * Checks whether a given line contains model data of the specified type.
     *
     * @param p_currentLine   The line to check for model data.
     * @param p_mapModelType The type of map model to check for.
     * @return true if the line contains model data of the specified type, false otherwise.
     */
    private boolean doLineHasModelData(String p_currentLine, MapModelTypes p_mapModelType) {
        return p_currentLine.substring(p_currentLine.indexOf("[") + 1, p_currentLine.indexOf("]"))
                .equalsIgnoreCase(p_mapModelType.getJsonValue());
    }

    /**
     * Retrieves model components from the given line.
     *
     * @param p_line The line to parse for model components.
     * @return A list of model components extracted from the line.
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
     * Executes the edit map command with the provided command values.
     *
     * @param p_commandValues The list of command values.
     * @return The response after executing the edit map command.
     * @throws InvalidMapException     If the map is invalid.
     * @throws ResourceNotFoundException If a required resource is not found.
     * @throws InvalidInputException   If the input is invalid.
     * @throws AbsentTagException      If a required tag is absent.
     * @throws EntityNotFoundException If an entity is not found.
     */
    @Override
    public String execute(List<String> p_commandValues)
            throws InvalidMapException,
            ResourceNotFoundException,
            InvalidInputException,
            AbsentTagException,
            EntityNotFoundException {
        String l_logResponse = "\n---EDITMAP---\n";
        String l_response = "";
        if (!p_commandValues.isEmpty()) {
            String resolvedPathToFile = FindFilePathUtil.resolveFilePath(p_commandValues.get(0));
            int l_index = resolvedPathToFile.lastIndexOf('\\');
            l_response = this.handleLoadMap(resolvedPathToFile);
            d_logEntryBuffer.dataChanged("editmap", l_logResponse + resolvedPathToFile.substring(l_index + 1) + " " + l_response + "\n");
            d_mapEditorEngine.setLoadingMap(false);
            return l_response;
        } else {
            throw new InvalidInputException("File name is empty!");
        }
    }
}
