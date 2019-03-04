package com.test.salman.androidassessment.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.salman.androidassessment.R;
import com.test.salman.androidassessment.constants.APIConstants;
import com.test.salman.androidassessment.entities.WeatherResponse.DailyData;
import com.test.salman.androidassessment.entities.WeatherResponse.WeatherResponseWrapper;
import com.test.salman.androidassessment.helper.helpers.APPHelper;
import com.test.salman.androidassessment.helper.helpers.PreferenceHelper;
import com.test.salman.androidassessment.helper.helpers.UIHelper;
import com.test.salman.androidassessment.helper.helpers.WeatherHelper;
import com.test.salman.androidassessment.helper.helpers.WeatherIcons;
import com.test.salman.androidassessment.helper.webhelper.WebRequest;
import com.test.salman.androidassessment.ui.adapter.DailyDayAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /*Global and Instance Varible
    * */
    private static final String TAG = "MainActivity";
    private static final int REQUEST_LOCATION = 1;

    RecyclerView recyclerView;
    Button restaurantBtn;
    LocationManager locationManager;
    String lattitude,longitude;
    private int precipitation,humidity;
    private double wind;
    String locality;
    DailyDayAdapter adapter;
    TextView countryTv,dayTv,summaryTv,tempTv,preciTv,humidityTv,windTv;
    ImageView weatherIcon;
    Context context;
    LinearLayout linear_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initWidgets
        initWidgets();

        //restaurant listener
        restaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go To Restaurant Screen
                Intent intent = new Intent(MainActivity.this,NearByRestaurantActivity.class);
                startActivity(intent);
            }
        });

        //request permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        //location manager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            UIHelper.buildAlertMessageNoGps(this);

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }

        //Set Daily Data
        setDailyData();


        /*
        * Check Internet Connection On Activity Start
        * */
        if(!APPHelper.isNetworkAvailable(this)){
            APPHelper.onNoInternet(linear_layout,false,"Not connected to internet");
        }else{
            APPHelper.onNoInternet(linear_layout,false,"Good! Connected to Internet");
        }
    }

    //Init Widgets
    private void initWidgets(){
        linear_layout = (LinearLayout) findViewById(R.id.linear_layout);
        recyclerView = (RecyclerView) findViewById(R.id.daily_day_rv);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        restaurantBtn = (Button) findViewById(R.id.restaurant_btn);
        weatherIcon = (ImageView) findViewById(R.id.weather_iv);
        countryTv = (TextView) findViewById(R.id.country_name_tv);
        dayTv = (TextView) findViewById(R.id.day_tv);
        summaryTv = (TextView) findViewById(R.id.weather_desc_tv);
        tempTv = (TextView) findViewById(R.id.temp_tv);
        preciTv = (TextView) findViewById(R.id.preciptation_tv);
        humidityTv = (TextView) findViewById(R.id.humidity_tv);
        windTv = (TextView) findViewById(R.id.wind_tv);
    }

    //Get Location
    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);

        } else {

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                Log.v("latitude",latti+"");
                Log.v("longitude",longi+"");
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Log.v( TAG,"Your current location is"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);
                setCityName(latti,longi);

            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                Log.v("latitude",latti+"");
                Log.v("longitude",longi+"");
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Log.e( TAG,"Your current location is"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);
//                //CALLING WEATHER DATA
//                getWeatherData();
                setCityName(latti,longi);


            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                Log.v("latitude",latti+"");
                Log.v("longitude",longi+"");

                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Log.e( TAG,"Your current location is"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);
                setCityName(latti,longi);

            }else{
                Toast.makeText(this,"Unable to Trace your location",Toast.LENGTH_SHORT).show();
            }

            //CALLING WEATHER API
            getWeatherData();
        }
    }

    //Get WeatherData
    private void getWeatherData(){

        PreferenceHelper.getInstance(this).savePlaceLatitude(lattitude);
        PreferenceHelper.getInstance(this).savePlaceLongitude(longitude);

        new WebRequest(this).getWeatherData(APIConstants.SECRET_KEY,Double.valueOf(lattitude), Double.valueOf(longitude),
                new Callback<WeatherResponseWrapper>() {
            @Override
            public void onResponse(Call<WeatherResponseWrapper> call, Response<WeatherResponseWrapper> response) {

              try {
                  if (response.body().toString() != null) {

                      if (response.body().getCurrently() != null) {
                          Log.e("Temperature", response.body().getCurrently().getTemperature() + "");
                          Log.e("Lat", "" + response.body().getLatitude() + "");
                          Log.e("Lng", "" + response.body().getLongitude() + "");

                          Long temperature = Math.round(response.body().getCurrently().getTemperature());
                          String currentTemp = String.format(Locale.ENGLISH, "%s°", temperature);
                          Log.e("lowTempFormatted", currentTemp + "");
                          String dayName = WeatherHelper.convertEpochToString(response.body().getCurrently().getTime(), "EEEE",
                                  "GMT-6:00");
                          String dayNameFormatted = String.format(Locale.ENGLISH, "%s", dayName);


                          long precipitation = Math.round(response.body().getCurrently().getPrecipProbability());
                          long windSpeed = Math.round(response.body().getCurrently().getWindSpeed());

                          tempTv.setText(currentTemp + "C"); //Current Temperature
                          summaryTv.setText(response.body().getCurrently().getSummary()); // Summary about Weather
                          dayTv.setText(dayNameFormatted); //Current Day Name
                          preciTv.setText(precipitation + "%");
                          humidityTv.setText(Math.round(response.body().getCurrently().getHumidity() * 100) + "%");
                          windTv.setText(String.valueOf(WeatherHelper.mpsToKmph(windSpeed)) + "km/h");

                          setWeatherIcon(response.body().getCurrently().getIcon());

                          Log.e("Precipitation", precipitation + "");
                          Log.e("humidity", humidity + "");
                          Log.e("Wind", String.valueOf(WeatherHelper.mpsToKmph(windSpeed)));

                          if (response.body().getDaily().getData().size() > 0) {
                              adapter.addAll(response.body().getDaily().getData());
                              adapter.notifyDataSetChanged();
                          }
                      } else {
                          Log.e(TAG, "Currently Weather Data is Null");
                      }

                  } else {
                      Log.e(TAG, "response body null");
                  }
              }catch (NullPointerException e){
                  e.printStackTrace();
              }
            }

            @Override
            public void onFailure(Call<WeatherResponseWrapper> call, Throwable t) {
                Log.e(TAG,"OnFailure");
                Log.e(TAG, t.getMessage());
            }
        });
    }

    //Get CityName
    private void setCityName(double lat,double lng){

        String cityName = "";
       try {
           Geocoder geocoder = new Geocoder(this, Locale.getDefault());
           List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
           if (addresses != null && addresses.size() > 0) {
               locality = addresses.get(0).getLocality();
               countryTv.setText(locality);
               PreferenceHelper.getInstance(this).savePlaceLocality(locality);
               Log.v("Locality",locality);
           }
       }catch (IOException e){
           e.printStackTrace();
       }
    }

    //Set WeatherIcon
    private void setWeatherIcon(String icon){
            Log.e("Icon",icon);
            HashMap<String, Drawable> weatherIcons = WeatherIcons.weatherIcons(this);

            try {
                for (int i = 0; i < weatherIcons.size(); i++) {
                    if (weatherIcons.containsKey(icon)) {
                        Log.e("IconData", weatherIcons.get(icon) + "");
                        weatherIcon.setImageDrawable(weatherIcons.get(icon));
                    }
                }
            }catch (NullPointerException e) {
                e.printStackTrace();
            }
    }

    //Set DailyData With Adapter
    private void setDailyData(){
        adapter = new DailyDayAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }


    /*
    * onResume
    * */
    @Override
    protected void onResume() {
        super.onResume();
    }
}
