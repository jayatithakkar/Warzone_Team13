package com.APP.Project.UserCoreLogic.Utility;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.APP.Project.UserCoreLogic.Exceptions.CoreLogicException;
import com.APP.Project.UserCoreLogic.Exceptions.InvalidInputException;
import com.APP.Project.UserCoreLogic.Exceptions.ResourceNotFoundException;

/*
 This class validates if user entered map file is exits or not. 
 If doesn't exits then throw and exception otherwise copy the details of .map file.

 @auther Bhoomi Bhatt
 @v 1.0
 */

public class FileValidationUtil {
	private static final String UPLOADED_FILE_EXTENSION = "map";

	// Take file extension and return valid .map file extension.
	public static String getUploadedFileExtension() {
		return UPLOADED_FILE_EXTENSION;
	}

	/*
	 * Verify that file name is valid or not.
	 * p_enteredFilePath is path of file where it exists.
	 * InvalidInputException throws incase file does not exists , throws
	 * ResourceNotFoundException if is unable to find
	 */
	public static File fetchFile(String p_enteredFilePath) throws Exception {
		File l_file = new File(FindFilePathUtil.findFilePath(p_enteredFilePath));
		String l_fileName = l_file.getName();
		checkIfFileExistsOrNot(l_file);

//throw an exception if user will unable to create a file.
		try{
			l_file.createNewFile();
		}catch(Exception p_e){
			throw new CoreLogicException("Sorry! you are not allowed to maeke ")
		}

		int l_lastIndex = l_fileName.lastIndexOf('.');
		if (l_lastIndex > 0) {
			String l_extend = l_fileName.substring(l_lastIndex + 1);

			if (!l_extend.equalsIgnoreCase(FileValidationUtil.getUploadedFileExtension())) {
				throw new InvalidInputException("Oops! Mentioned file does not exist!!!");
			} else {
				return l_file;
			}
		} else {
			throw new InvalidInputException("Error! Entered file must have an extension!!!");
		}

	}

	/*
	 * It checks if file exits or not
	 * If file is unable to find and throws ResourceNotFoundException if file is not
	 * found.
	 * If file exits then it will return true.
	 */

	private static boolean checkIfFileExistsOrNot(File p_fileInstance) throws ResourceNotFoundException {
		if (!p_fileInstance.exists()) {
			throw new ResourceNotFoundException("Mentioned file does not exist!!!");
		}
		return true;
	}

	/*
	 * Copies the file from source to destination or we can say overwrites the
	 * destination file.
	 * 
	 * p_fileCopyFrom is source path
	 * p_fileCopyTo_destination destination path to the file
	 */
	public static void copy(Path p_fileCopyFrom, Path p_fileCopyTo_destination) {
		try {
			Files.copy(p_fileCopyFrom, p_fileCopyTo_destination, StandardCopyOption.ATOMIC_MOVE);
		} catch (Exception l_skip) {
			// Ignore the exception while the file is being copied
		}
	}

}
