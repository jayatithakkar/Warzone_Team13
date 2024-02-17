package com.APP.Project.UserCoreLogic.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class file is responsible for displaying current date and time.
 * 
 * @author Bhoomiben Bhatt
 * @verson 1.0
 */
public class DataTimeUtil {
    /**
     * Gets the current Date and time
     * 
     * @return value of readable time string
     */
    public static String getDateTime() {
        SimpleDateFormat currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        return currentDateTime.format(new Date());
    }
}
