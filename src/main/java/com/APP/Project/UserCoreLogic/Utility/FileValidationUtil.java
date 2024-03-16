package com.APP.Project.UserCoreLogic.Utility;

import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.exceptions.ResourceNotFoundException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Provides utility methods for resolving file paths within user-defined data
 * and log directories.
 * This class implements a singleton pattern to ensure a single instance manages
 * path resolutions throughout the application. It automatically creates user
 * data and log directories if they do not exist, using paths based on the
 * user's home directory. This utility is designed to centralize the management
 * of file paths, facilitating easier access and manipulation of files within
 * these directories.
 *
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
public class FileValidationUtil {
    private static final String MAP_FILE_EXTENSION = "map";

    /**
     * Retrieves the designated file extension for valid files.
     *
     * @return A {@code String} representing the valid file extension.
     */
    public static String getFileExtension() {
        return MAP_FILE_EXTENSION;
    }

    /**
     * Checks whether the given file name is valid or not.
     *
     * @param p_filePath Value of the path to file.
     * @return Value of File object for the file given with path.
     * @throws InvalidInputException     Throws if the file does not exist.
     * @throws ResourceNotFoundException Throws if file can not be created.
     */
    public static File retrieveFile(String p_filePath) throws ResourceNotFoundException, InvalidInputException {
        File l_file = new File(p_filePath);
        String l_fileName = l_file.getName();
        try {
            l_file.createNewFile();
        } catch (Exception p_exception) {
            throw new ResourceNotFoundException("Can not create a file due to file permission!");
        }

        try {
            if (checksIfFileHasRequiredExtension(l_fileName)) {
                return l_file;
            }
        } catch (InvalidInputException p_invalidInputException) {
            throw p_invalidInputException;
        }

        throw new InvalidInputException("Invalid file!");
    }

    /**
     * Validates whether the provided file name has the required extension.
     *
     * @param p_fileName The name of the file as a {@code String}.
     * @return {@code true} if the file has the required extension; otherwise {@code false}.
     * @throws InvalidInputException if the file name is invalid or does not have an extension.
     */
    public static boolean checksIfFileHasRequiredExtension(String p_fileName) throws InvalidInputException {
        int l_index = p_fileName.lastIndexOf('.');
        if (l_index > 0) {
            char l_prevChar = p_fileName.charAt(l_index - 1);
            if (l_prevChar != '.') {
                String l_extension = p_fileName.substring(l_index + 1);
                if (!l_extension.equalsIgnoreCase(FileValidationUtil.getFileExtension())) {
                    throw new InvalidInputException("File doesn't exist!");
                }
                return true;
            }
        }
        throw new InvalidInputException("File must have an extension!");
    }

    /**
     * Creates a new file at the specified path if it does not already exist.
     *
     * @param p_filePath The file path as a {@code String}.
     * @return A {@code File} object for the newly created file.
     * @throws ResourceNotFoundException if the file cannot be created.
     */
    public static File createFileIfNotExists(String p_filePath) throws ResourceNotFoundException {
        File l_file = new File(p_filePath);
        try {
            l_file.createNewFile();
        } catch (Exception p_exception) {
            throw new ResourceNotFoundException("File can not be created!");
        }
        return l_file;
    }

    /**
     * Checks if the file exists or not.
     *
     * @param p_fileObject Value of the file object.
     * @return True if the file exists; otherwise throws an exception.
     * @throws ResourceNotFoundException Throws if file not found.
     */
    private static boolean checkIfFileExists(File p_fileObject) throws ResourceNotFoundException {
        if (!p_fileObject.exists()) {
            throw new ResourceNotFoundException("File doesn't exist!");
        }
        return true;
    }

    /**
     * Copies the file from source path to the destination. This method replaces the existing file at destination path.
     *
     * @param p_source Source path to the file.
     * @param p_dest   Destination path to the file.
     */
    public static void copy(Path p_source, Path p_dest) {
        try {
            // Ignore if the file already exists.
            if (!(new File(p_dest.toUri().getPath()).exists())) {
                Files.copy(p_source, p_dest, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception l_ignored) {
            // Ignore the exception while copying.
        }
    }
}
