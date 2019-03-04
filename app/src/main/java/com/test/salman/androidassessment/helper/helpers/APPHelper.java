package com.test.salman.androidassessment.helper.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.test.salman.androidassessment.R;
import com.test.salman.androidassessment.entities.NearByRestaurantResponse.Photo;
import com.test.salman.androidassessment.entities.NearByRestaurantResponse.Result;
import com.test.salman.androidassessment.entities.WeatherResponse.DailyData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Salman on 3/4/2019.
 */

public class APPHelper {


    /*
    * Swap NearByRestaurant List for Top Five Star Restaurants
    *
    * */
    public static List<Result> swapListOfFiveStarRestaurant(List<Result> list){

         int count = 0;
         try {
             if (list.size() > 0) {
                 for (int i = 0; i < list.size(); i++) {
                     if (list.get(i).getRating() == 5.0) {
                         if (i > 2) {
                             if (count < 3) {
                                 Collections.swap(list, count, i);
                                 count++;
                             }
                         }
                     }
                 }
             }
         }catch (IndexOutOfBoundsException e){
             e.printStackTrace();
         }

        return list;
    }

    /*
    * Check String is Null or Not
    *
    * */
    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }


    /*
    * Set String Value If String Is Empty Or Null
    *
    * */
    public static String isStringEmptyOrNull(String str){

        if(isNullOrEmpty(str)){
            str = "N/A";
        }
        return str;
    }

    /*
    * Check & Set Value Of Integer If It Is Null
    *
    * */
    public static Integer isIntegerNull(Integer i){
        if(i == null){
            i = 0;
        }
        return i;
    }



    /*
    * Check IsNetworkAvailable
    * */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager == null)
            return false;

        // 3g-4g available
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        // wifi available
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();

        System.out.println(is3g + " net " + isWifi);

        if (!is3g && !isWifi) {
            return false;
        } else
            return true;

    }


    /*
    * onNoInternet
    *
    * */

    public static void onNoInternet(View view, boolean isSuccess, String message) {
        if (isSuccess) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();

        } else
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();

    }





}
