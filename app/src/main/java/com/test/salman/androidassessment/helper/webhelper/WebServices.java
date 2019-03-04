package com.test.salman.androidassessment.helper.webhelper;

import com.test.salman.androidassessment.constants.APIConstants;
import com.test.salman.androidassessment.entities.NearByRestaurantResponse.NearByRestWrapper;
import com.test.salman.androidassessment.entities.WeatherResponse.WeatherResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Salman on 3/2/2019.
 */

public interface WebServices {


    //Current Weather
    @GET(APIConstants.CURRENT_WEATHER +"{key}/{latitude},{longitude}")
    Call<WeatherResponseWrapper> getWeatherResponse(@Path(APIConstants.KEY)  String key,
                                                    @Path(APIConstants.LATITUDE) Double latitude,
                                                    @Path(APIConstants.LONGITUDE) Double longitude,
                                                    @Query(APIConstants.UNITS)  String units,
                                                    @Query(APIConstants.EXCLUDE) String exclude);


    //Near By Restaurant
    @GET
    Call<NearByRestWrapper> getNearByRestaurant(@Url String url,
                                                @Query(APIConstants.SENSOR)  String sensor,
                                                @Query(APIConstants.KEY)  String key,
                                                @Query(APIConstants.TYPE)  String type,
                                                @Query(APIConstants.LOCATION)  String location,
                                                @Query(APIConstants.RADIUS)  String radius);



}
