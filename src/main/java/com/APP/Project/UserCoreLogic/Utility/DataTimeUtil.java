package com.APP.Project.UserCoreLogic.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class provides utility for <code>Date</code> and <code>Time</code>.
 *
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
public class DataTimeUtil {
    /**
     * This method returns the current date and time in the format "yyyy-MM-dd
     * HH:mm:ss:SS",
     *
     * @return Value of readable time string.
     */
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        return format.format(new Date());
    }
}