package com.test.salman.androidassessment.helper.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.test.salman.androidassessment.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Salman on 3/3/2019.
 */

public class WeatherIcons {


    /*
    * Match Weather Icons
    * */
    public static HashMap<String,Drawable> weatherIcons(Context context){

        HashMap<String,Drawable> weatherIconMap = new HashMap<String,Drawable>();

        weatherIconMap.put("clear-night",ContextCompat.getDrawable(context,R.drawable.ic_weather_clear_day));
        weatherIconMap.put("clear-day",ContextCompat.getDrawable(context,R.drawable.ic_weather_sunny_day));
        weatherIconMap.put("rain",ContextCompat.getDrawable(context, R.drawable.ic_weather_rain));
        weatherIconMap.put("snow",ContextCompat.getDrawable(context, R.drawable.ic_weather_snow));
        weatherIconMap.put("sleet",ContextCompat.getDrawable(context, R.drawable.ic_weather_sleet));
        weatherIconMap.put("wind",ContextCompat.getDrawable(context, R.drawable.ic_weather_wind));
        weatherIconMap.put("fog",ContextCompat.getDrawable(context, R.drawable.ic_weather_fog));
        weatherIconMap.put("cloudy",ContextCompat.getDrawable(context, R.drawable.ic_weather_cloudy));
        weatherIconMap.put("partly-cloudy-day",ContextCompat.getDrawable(context, R.drawable.ic_weather_partly_cloudy_day));
        weatherIconMap.put("partly-cloudy-night",ContextCompat.getDrawable(context, R.drawable.ic_weather_party_cloudy_night));
        weatherIconMap.put("breez",ContextCompat.getDrawable(context, R.drawable.ic_weather_air_wind));

       return weatherIconMap;
    }
}
