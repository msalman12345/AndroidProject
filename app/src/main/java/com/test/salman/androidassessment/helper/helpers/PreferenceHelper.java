package com.test.salman.androidassessment.helper.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Salman on 3/3/2019.
 */

public class PreferenceHelper {

    private static PreferenceHelper sharePref = new PreferenceHelper();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String PLACE_OBJ = "place_obj";
    private static final String PLACE_OBJ_LATITUDE = "latitude";
    private static final String PLACE_OBJ_LONGITUDE = "longitude";
    private static final String PLACE_OBJ_LOCALITY = "locality";

    private PreferenceHelper() {} //prevent creating multiple instances by making the constructor private

    //The context passed into the getInstance should be application level context.
    public static PreferenceHelper getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharePref;
    }

    public void savePlaceLatitude(String placeObjStr) {
        editor.putString(PLACE_OBJ_LATITUDE, placeObjStr);
        editor.commit();
    }

    public String getPlaceLatitude() {
        return sharedPreferences.getString(PLACE_OBJ_LATITUDE, "");
    }

    public void savePlaceLongitude(String placeObjStr) {
        editor.putString(PLACE_OBJ_LONGITUDE, placeObjStr);
        editor.commit();
    }

    public String getPlaceLongitude() {
        return sharedPreferences.getString(PLACE_OBJ_LONGITUDE, "");
    }

    public void savePlaceLocality(String placeObjStr) {
        editor.putString(PLACE_OBJ_LOCALITY, placeObjStr);
        editor.commit();
    }

    public String getPlaceLocality() {
        return sharedPreferences.getString(PLACE_OBJ_LOCALITY, "");
    }

    public void removePlaceLatitude() {
        editor.remove(PLACE_OBJ_LATITUDE);
        editor.commit();
    }

    public void removePlaceLongitude() {
        editor.remove(PLACE_OBJ_LONGITUDE);
        editor.commit();
    }

    public void removePlaceLocality() {
        editor.remove(PLACE_OBJ_LOCALITY);
        editor.commit();
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }

}
