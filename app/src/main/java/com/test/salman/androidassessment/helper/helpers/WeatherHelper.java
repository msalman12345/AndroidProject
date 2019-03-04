package com.test.salman.androidassessment.helper.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Salman on 3/3/2019.
 */

public class WeatherHelper {

    public static String convertEpochToString(Integer epoch, String format, String timezone) {
        // Convert the epoch to a long and then create a new date with it.
        Date date = new Date(epoch.longValue() * 1000);

        // Create a new calendar using the provided timezone.
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone));

        // Add the new date to the calendar.
        calendar.setTime(date);

        // Return a formatted date string using SimpleDateFormat and the provided format.
        return new SimpleDateFormat(format, Locale.ENGLISH).format(calendar.getTime());

    }

    // method to convert speed
    public static int mpsToKmph(double mps)
    {
        return(int) (3.6 * mps);
    }

    //Humidity
    public static int humidity(double humidity)
    {
        return(int) (humidity * 100);
    }



}
