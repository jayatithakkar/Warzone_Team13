package com.APP.Project.UserCoreLogic.utilities;

import java.nio.file.*;

public class PathResolverUtil {
     private static PathResolverUtil d_pathInstance;

     private Path d_user_data_directory_path;

     private String d_user_data_directory = "";

     private PathResolverUtil() {
          Path absolute = relative.toAbsolutePath();
          d_user_data_directory_path = Paths.get(System.getProperty("user.home"), "Downloads", d_user_data_directory);
          
     }

     public static PathResolverUtil getInstance() {
          if (d_pathInstance == null) {
               d_pathInstance = new PathResolverUtil();
          }
          return d_pathInstance;
     }

     public static Path getUserDataDirectoryPath() {
          return PathResolverUtil.getInstance().d_user_data_directory_path;
     }

     public static String resolveFilePath(String p_filePath) {
          return Paths.get(getUserDataDirectoryPath().toString(), p_filePath).toString();
     }
}
