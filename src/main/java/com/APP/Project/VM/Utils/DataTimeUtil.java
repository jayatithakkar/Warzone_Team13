package com.APP.Project.VM.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeUtil {
    public static String getDateTime() {
        SimpleDateFormat currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        return currentDateTime.format(new Date());
    }
}
