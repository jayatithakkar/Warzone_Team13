package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.UserCoreLogic.constants.interfaces.StandaloneCommand;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.logger.LogEntryBuffer;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.Utility.FindFilePathUtil;

import java.util.List;


/**
 * LoadMapAdapter class implements the StandaloneCommand interface to handle the loading of map features.
 * This adapter is responsible for executing the loading of map features based on the provided command values.
 * It interacts with EditMapAdapter and ValidateMapAdapter to ensure the correctness of the loaded map.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class LoadMapAdapter implements StandaloneCommand {
    private LogEntryBuffer d_logEntryBuffer=LogEntryBuffer.getLogger();

    /**
     * Executes the loading of map features based on the provided command values.
     *
     * @param p_commandValues List of command values where the first value is expected to be the file path.
     * @return Response message after loading the map features.
     * @throws InvalidMapException     If the loaded map is invalid.
     * @throws ResourceNotFoundException If the required resources are not found.
     * @throws InvalidInputException   If the input provided is invalid.
     * @throws AbsentTagException      If the required tags are absent.
     * @throws EntityNotFoundException If entities are not found in the map.
     */
    @Override
    public String execute(List<String> p_commandValues)
            throws InvalidMapException,
            ResourceNotFoundException,
            InvalidInputException,
            AbsentTagException,
            EntityNotFoundException {
        try {
            EditMapAdapter l_editMapService = new EditMapAdapter();
            String resolvedPathToFile = FindFilePathUtil.resolveFilePath(p_commandValues.get(0));
            String response = l_editMapService.handleLoadMap(resolvedPathToFile, false);

            try {
                ValidateMapAdapter l_validateObj = new ValidateMapAdapter();
                l_validateObj.execute(null,"loadmap");
            } catch (InvalidMapException | EntityNotFoundException l_e) {
                MapFeatureEngine.getInstance().initialise();
                throw l_e;
            }
            d_logEntryBuffer.dataChanged("loadmap","\n---LOADMAP---\n"+response+"\n");
            return response;
        } catch (ArrayIndexOutOfBoundsException p_e) {
            throw new InvalidInputException("File name is empty!");
        }
    }
}
