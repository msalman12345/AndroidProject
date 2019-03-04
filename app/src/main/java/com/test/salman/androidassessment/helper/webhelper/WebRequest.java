package com.test.salman.androidassessment.helper.webhelper;

import android.app.Activity;

import com.test.salman.androidassessment.BuildConfig;
import com.test.salman.androidassessment.constants.APIConstants;
import com.test.salman.androidassessment.entities.NearByRestaurantResponse.NearByRestWrapper;
import com.test.salman.androidassessment.entities.WeatherResponse.WeatherResponseWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Salman on 3/2/2019.
 */

public class WebRequest {

    private WebServices apiServiceForm;
    Activity activity;

    public WebRequest(Activity activity) {

        this.activity = activity;
        if (apiServiceForm == null) {

//            final BasePreferenceHelper preHelper = new BasePreferenceHelper(activity);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(2, TimeUnit.MINUTES);
            httpClient.readTimeout(2, TimeUnit.MINUTES);


            // add your other interceptors …
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request original = chain.request();
                    // Request customization: add request headers
                    Request request = null;

                    try {

                        Request.Builder requestBuilder = original.newBuilder();
//                        if (preHelper.getUser() != null) {
//                            requestBuilder.addHeader("Authorization", "" + preHelper.getToken());
//                        }
                        request = requestBuilder.build();


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return chain.proceed(request);

                }
            });

            if (BuildConfig.DEBUG) {
                httpClient.addInterceptor(logging);
            }
            apiServiceForm = new Retrofit.Builder()
                    .baseUrl(APIConstants.WEATHER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build().create(WebServices.class);

        }

    }


    //GetWeatherData
    public void getWeatherData(String key,Double lat,Double lng,Callback<WeatherResponseWrapper> response) {

        String str = "minutely,hourly,alerts,flags";

        apiServiceForm.getWeatherResponse(key,lat,lng,"si","").enqueue(response);

    }

    //GetNearByRestaurantData
    public void getNearByRestaurant(String sensor,String key,String type,String location,String radius,
                                    Callback<NearByRestWrapper> response){

        apiServiceForm.getNearByRestaurant(APIConstants.NEAR_BY_RESTAURANT,
                                           sensor,key,type,location,radius).enqueue(response);
    }

}
