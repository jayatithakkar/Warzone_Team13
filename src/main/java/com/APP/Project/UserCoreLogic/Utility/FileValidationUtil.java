package com.APP.Project.UserCoreLogic.Utility;

import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.exceptions.ResourceNotFoundException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Utility class for file validation and operations.
 *
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
public class FileValidationUtil {
	/** The file extension for map files. */
	private static final String MAP_FILE_EXTENSION = "map";

	/**
     * Retrieves the file extension.
     *
     * @return The file extension
     */
	public static String getFileExtension() {
		return MAP_FILE_EXTENSION;
	}

	/**
     * Retrieves the file object from the given file path.
     *
     * @param p_filePath The file path
     * @return The file object
     * @throws ResourceNotFoundException If the file cannot be found
     * @throws InvalidInputException If the input is invalid
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
     * Checks if the file has the required extension.
     *
     * @param p_fileName The file name
     * @return True if the file has the required extension, otherwise false
     * @throws InvalidInputException If the input is invalid
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
     * Creates a file if it does not exist.
     *
     * @param p_filePath The file path
     * @return The created file object
     * @throws ResourceNotFoundException If the file cannot be found
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
     * Checks if the file exists.
     *
     * @param p_fileObject The file object
     * @return True if the file exists, otherwise false
     * @throws ResourceNotFoundException If the file cannot be found
     */
	private static boolean checkIfFileExists(File p_fileObject) throws ResourceNotFoundException {
		if (!p_fileObject.exists()) {
			throw new ResourceNotFoundException("File doesn't exist!");
		}
		return true;
	}

	/**
     * Copies a file from source to destination.
     *
     * @param p_source The source path
     * @param p_dest The destination path
     */
	public static void copy(Path p_source, Path p_dest) {
		try {
			if (!(new File(p_dest.toUri().getPath()).exists())) {
				Files.copy(p_source, p_dest, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception l_ignored) {
		}
	}
}
