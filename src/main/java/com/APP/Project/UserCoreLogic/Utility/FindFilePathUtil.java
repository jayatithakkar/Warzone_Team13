package com.APP.Project.UserCoreLogic.Utility;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FindFilePathUtil {
	private static FindFilePathUtil d_Object;

	private Path d_dataDirectoryPath;

	private String d_dataDirectory = "";

	private FindFilePathUtil() {
		d_dataDirectoryPath = Paths.get(System.getProperty("user.home"), "Downloads", d_dataDirectory);
	}

	public static FindFilePathUtil getInstance() {
		if (d_Object == null) {
			d_Object = new FindFilePathUtil();
		}
		return d_Object;
	}

	public static Path getDataDirectoryPath() {
		return FindFilePathUtil.getInstance().d_dataDirectoryPath;
	}

	public static String findFilePath(String p_filePath) {
		return Paths.get(getDataDirectoryPath().toString(), p_filePath).toString();
	}

}