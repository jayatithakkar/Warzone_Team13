package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.constants.interfaces.StandaloneCommand;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.Utility.FindFilePathUtil;

import java.util.List;

/**
 * LoadMapAdapter class implements the StandaloneCommand interface and defines
 * the behavior for loading a map.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 * 
 */
public class LoadMapAdapter implements StandaloneCommand {
    
    /**
     * Executes the command to load a map.
     *
     * @param p_commandValues a list of command values containing the path to the map file
     * @return a response indicating the outcome of the load operation
     * @throws InvalidMapException    if the loaded map is invalid
     * @throws ResourceNotFoundException if the required resource is not found
     * @throws InvalidInputException  if the input provided is invalid
     * @throws AbsentTagException     if a required tag is absent
     * @throws EntityNotFoundException if the entity is not found
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
            String resolvedPathToFile = FindFilePathUtil.findFilePath(p_commandValues.get(0));
            String response = l_editMapService.handleLoadMap(resolvedPathToFile, false);

            try {
                ValidateMapAdapter l_validateObj = new ValidateMapAdapter();
                l_validateObj.execute(null);
            } catch (InvalidMapException | EntityNotFoundException l_e) {
                MapFeatureEngine.getInstance().initialise();
                throw l_e;
            }

            Main.VIRTUAL_MACHINE().setGameStatePlaying();
            return response;
        } catch (ArrayIndexOutOfBoundsException p_e) {
            throw new InvalidInputException("File name is empty!");
        }
    }
}
