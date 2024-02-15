package com.APP.Project.UserCoreLogic.utilities;

import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.exceptions.ResourceNotFoundException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    private static final String MAP_FILE_EXTENSION = "map";

    public static String getFileExtension() {
        return MAP_FILE_EXTENSION;
    }

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

    public static boolean checksIfFileHasRequiredExtension(String p_fileName) throws InvalidInputException {
        int l_index = p_fileName.lastIndexOf('.');
        if (l_index > 0) {
            char l_prevChar = p_fileName.charAt(l_index - 1);
            if (l_prevChar != '.') {
                String l_extension = p_fileName.substring(l_index + 1);
                if (!l_extension.equalsIgnoreCase(FileUtil.getFileExtension())) {
                    throw new InvalidInputException("File doesn't exist!");
                }
                return true;
            }
        }
        throw new InvalidInputException("File must have an extension!");
    }

    public static File createFileIfNotExists(String p_filePath) throws ResourceNotFoundException {
        File l_file = new File(p_filePath);
        try {
            l_file.createNewFile();
        } catch (Exception p_exception) {
            throw new ResourceNotFoundException("File can not be created!");
        }
        return l_file;
    }

    private static boolean checkIfFileExists(File p_fileObject) throws ResourceNotFoundException {
        if (!p_fileObject.exists()) {
            throw new ResourceNotFoundException("File doesn't exist!");
        }
        return true;
    }

    public static void copy(Path p_source, Path p_dest) {
        try {
            if (!(new File(p_dest.toUri().getPath()).exists())) {
                Files.copy(p_source, p_dest, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception l_ignored) {
        }
    }
}
