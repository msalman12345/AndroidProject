package com.test.salman.androidassessment.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Salman on 3/2/2019.
 */

public class BaseApplication extends Application{


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {

        super.onCreate();
        MultiDex.install(this);
//        TextUtility.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Light.ttf"); // font from assets: "assets/fonts/Roboto_Regular.ttf

    }


}
