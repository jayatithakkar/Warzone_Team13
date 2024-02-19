package com.APP.Project.UserCoreLogic.Utility;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.APP.Project.UserCoreLogic.exceptions.CoreLogicException;
import com.APP.Project.UserCoreLogic.exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.exceptions.ResourceNotFoundException;

/**
 This class validates if user entered map file is exits or not. 
 If doesn't exits then throw and exception otherwise copy the details of .map file.

 @author Bhoomiben Bhatt
 */

public class FileValidationUtil {
	private static final String UPLOADED_FILE_EXTENSION = "map";

	/**
	 * Take given file extension
	 * 
	 * @return valid .map file extension.
	 */
	public static String getUploadedFileExtension() {
		return UPLOADED_FILE_EXTENSION;
	}


	/**
	 * Verify that file name is valid or not.
	 * 
	 * @param p_enteredFilePath is path of file where it exists.
	 * 
	 * @throws InvalidInputException     throws incase file does not exists
	 * @throws ResourceNotFoundException if is unable to create a file
	 */
	public static File fetchFile(String p_enteredFilePath) throws ResourceNotFoundException, InvalidInputException {
		File l_file = new File(p_enteredFilePath);
		String l_fileName = l_file.getName();

		// throw an exception if user will unable to create a file.
		try {
			l_file.createNewFile();
		} catch (Exception p_e) {
			throw new ResourceNotFoundException("Sorry! you are not allowed to create a file ");
		}

		try {
			if (VerifyIfFileHasRequiredExtensionOrNot(l_fileName)) {
				return l_file;
			}
		} catch (InvalidInputException p_invalidInputException) {
			throw p_invalidInputException;
		}

		throw new InvalidInputException("Not a valid File!");
	}

	
	/**
	 * 
	 * checks if file has valid extension or not
	 * 
	 * @param p_fileName name of file
	 * @return True if file has valid passed parameters arguent or false
	 * @throws InvalidInputException if file name is inappropriate
	 */
	public static boolean VerifyIfFileHasRequiredExtensionOrNot(String p_fileName) throws InvalidInputException {
		int l_lastindex = p_fileName.lastIndexOf('.');
		if (l_lastindex > 0) {
			char l_formerChar = p_fileName.charAt(l_lastindex - 1);
			if (l_formerChar != '.') {
				String l_fileExtension = p_fileName.substring(l_lastindex + 1);
				if (!l_fileExtension.equalsIgnoreCase(FileValidationUtil.getUploadedFileExtension())) {
					throw new InvalidInputException("oops! Entered file does not exsits!");
				}
				return true;
			}
		}
		throw new InvalidInputException("This file is must be having some valid extension");
	}


	/**
	 * create a file if found to be non exist
	 * 
	 * @param p_filePath is path of fle
	 * @return instance of class
	 * @throws ResourceNotFoundExcetion if existace of a file not found
	 */
	public static File createFileIfNotExits(String p_filePath) throws ResourceNotFoundException {
		File l_file = new File(p_filePath);
		try {
			l_file.createNewFile();
		} catch (Exception p_e) {
			throw new ResourceNotFoundException("Sorry! No resource found so.. file can not be created.");
		}
		return l_file;
	}

	/**
	 * It checks if file exits or not
	 * 
	 * @param p_fileInstace If file is unable to find
	 * @return True if file exist otherwise throw an exception
	 * @throw ResourceNotFoundException if file is not found.
	 */
	private static boolean checkIfFileExistsOrNot(File p_fileInstace) throws ResourceNotFoundException {
		if (!p_fileInstace.exists()) {
			throw new ResourceNotFoundException("Mentioned file does not exist!!!");
		}
		return true;
	}

	/**
	 * Copies the file from source to destination or we can say overwrites the
	 * destination file.
	 * 
	 * @param p_fileCopyFrom           is source path
	 * @param p_fileCopyTo_destination destination path to the file
	 */
	public static void copy(Path p_fileCopyFrom, Path p_fileCopyTo_destination) {
		try {
			if (!(new File(p_fileCopyTo_destination.toUri().getPath()).exists())) {
				Files.copy(p_fileCopyFrom, p_fileCopyTo_destination, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception l_skip) {
			// Ignore the exception while the file is being copied
		}
	}

}
