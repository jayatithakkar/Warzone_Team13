package com.APP.Project.UserCoreLogic.Utility;

import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * This class helps user to save/load important files or documents/maps inside downloades folder [a person can change the location if he/she wants]
 */
public class FindFilePathUtil {
	private static FindFilePathUtil d_Object;

	// object of a class is created
	private Path d_dataDirectoryPath;

	// file store in Downloads
	private String d_dataDirectory = "";

	// data stored inside c->users-> home ->downloads
	private FindFilePathUtil() {
		d_dataDirectoryPath = Paths.get(System.getProperty("user.home"), "Downloads", d_dataDirectory);
	}

	// if intance of class is null or referance not provided then program execution
	// start with making a referance.
	public static FindFilePathUtil getInstance() {
		if (d_Object == null) {
			d_Object = new FindFilePathUtil();
		}
		return d_Object;
	}

	// take string value of user data directory
	// return path value
	public static Path getDataDirectoryPath() {
		return FindFilePathUtil.getInstance().d_dataDirectoryPath;
	}

	/*
	 * using user data directly, user can resolves absolute path to file
	 */
	public static String findFilePath(String p_filePath) {
		return Paths.get(getDataDirectoryPath().toString(), p_filePath).toString();
	}

}