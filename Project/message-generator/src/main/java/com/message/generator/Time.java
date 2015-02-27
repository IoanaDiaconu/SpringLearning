package com.message.generator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ioana.diaconu on 2/27/2015.
 */
public class Time {
    public long getTime(){
        return System.currentTimeMillis();
    }

    public static String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
