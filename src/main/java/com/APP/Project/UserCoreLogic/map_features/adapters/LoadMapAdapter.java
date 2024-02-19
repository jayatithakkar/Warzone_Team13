package com.APP.Project.UserCoreLogic.map_features.adapters;

import com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.constants.interfaces.StandaloneCommand;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;
import com.APP.Project.UserCoreLogic.Utility.FindFilePathUtil;

import java.util.List;

/**
 * This file loads map file in the user console.
 * <p>
 * This service handles `loadmap` user command.
 *
 * @author Brijesh Lakkad
 */
public class LoadMapAdapter implements StandaloneCommand {
    /**
     * Handles the load map operation for user command.
     *
     * @param p_commandValues Represents the values passed while running the command.
     * @return Value of string acknowledging user that the file is loaded or not.
     * @throws InvalidMapException       Throws if the map was not valid.
     * @throws ResourceNotFoundException Throws if file not found.
     * @throws InvalidInputException     Throws if the user command is invalid.
     * @throws AbsentTagException        Throws if any tag is missing in map file.
     * @throws EntityNotFoundException   Throws if entity is missing
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
            // Resolve file path using absolute path of user data directory.
            String resolvedPathToFile = FindFilePathUtil.findFilePath(p_commandValues.get(0));
            String response = l_editMapService.handleLoadMap(resolvedPathToFile, false);

            try {
                // Validates the map before saving the file.
                ValidateMapAdapter l_validateObj = new ValidateMapAdapter();
                l_validateObj.execute(null);
            } catch (InvalidMapException | EntityNotFoundException l_e) {
                MapFeatureEngine.getInstance().initialise();
                throw l_e;
            }

            /*
             * Sets the game state to <code>Game Play</code>
             */
            Main.VIRTUAL_MACHINE().setGameStatePlaying();
            return response;
        } catch (ArrayIndexOutOfBoundsException p_e) {
            throw new InvalidInputException("File name is empty!");
        }
    }
}
