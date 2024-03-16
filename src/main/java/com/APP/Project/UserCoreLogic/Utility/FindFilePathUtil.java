package com.APP.Project.UserCoreLogic.Utility;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility class for managing and resolving file paths related to the user's
 * data and log directories.
 * This class encapsulates the functionality to manage directories and resolve
 * file paths within a user-defined
 * data directory and a specified log directory. It utilizes a singleton pattern
 * to ensure that a single instance
 * manages the paths consistently across the application.
 *
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
public class FindFilePathUtil {
	/**
	 * Singleton instance of the class for global access.
	 */
	private static FindFilePathUtil d_Instance;

	/**
	 * The Path object representing the user's data directory.
	 */
	private final Path USER_DATA_DIRECTORY_PATH;

	/**
	 * The relative or absolute path to the user data directory as a String.
	 * This is initialized as an empty string and can be set to a specific
	 * directory.
	 */
	private final String USER_DATA_DIRECTORY = "";

	/**
	 * The name of the directory where log files will be saved.
	 */
	private final String USER_LOG_DIRECTORY = "War_Zone_Logs";

	/**
	 * The Path object representing the directory for log files.
	 */
	private final Path USER_LOG_DIRECTORY_PATH;

	/**
	 * Private constructor to prevent external instantiation and to initialize
	 * directory paths.
	 * Creates the user data and log directories if they do not exist.
	 */
	private FindFilePathUtil() {
		USER_DATA_DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), "Downloads", USER_DATA_DIRECTORY);
		USER_LOG_DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), "Downloads", USER_LOG_DIRECTORY);

		this.createDirectory(USER_DATA_DIRECTORY_PATH.toString());
		this.createDirectory(USER_LOG_DIRECTORY_PATH.toString());
	}

	/**
	 * Creates a directory at the specified file path if it does not already exist.
	 *
	 * @param p_filePath The path where the directory is to be created.
	 */
	public void createDirectory(String p_filePath) {
		File l_file = new File(p_filePath);
		if (!l_file.exists()) {
			l_file.mkdir();
		}
	}

	/**
	 * Provides access to the singleton instance of the FindFilePathUtil class.
	 *
	 * @return The single instance of FindFilePathUtil.
	 */
	public static FindFilePathUtil getInstance() {
		if (d_Instance == null) {
			d_Instance = new FindFilePathUtil();
		}
		return d_Instance;
	}

	/**
	 * Retrieves the path to the user data directory.
	 *
	 * @return The Path object representing the user data directory.
	 */
	public static Path getUserDataDirectoryPath() {
		return FindFilePathUtil.getInstance().USER_DATA_DIRECTORY_PATH;
	}

	/**
	 * Retrieves the path to the log directory.
	 *
	 * @return The Path object representing the log directory.
	 */
	public static Path getLogDirectoryPath() {
		return FindFilePathUtil.getInstance().USER_LOG_DIRECTORY_PATH;
	}

	/**
	 * Resolves the absolute path to a file within the user data directory.
	 *
	 * @param p_filePath The name or relative path of the file within the user data
	 *                   directory.
	 * @return The absolute path to the file as a String.
	 */
	public static String resolveFilePath(String p_filePath) {
		return Paths.get(getUserDataDirectoryPath().toString(), p_filePath).toString();
	}

	/**
	 * Resolves the absolute path to a file within the log directory.
	 *
	 * @param p_filePath The name or relative path of the file within the log
	 *                   directory.
	 * @return The absolute path to the file as a String.
	 */
	public static String resolveLogPath(String p_filePath) {
		return Paths.get(getLogDirectoryPath().toString(), p_filePath).toString();
	}
}