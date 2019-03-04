package com.test.salman.androidassessment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.test.salman.androidassessment.R;

/**
 * Created by Salman on 3/4/2019.
 */

public class SplashActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Calling Init Widgets
        initWidgets();

        //Calling StartAnim Method
        startAnim();
    }

    private void initWidgets(){
        textView = (TextView) findViewById(R.id.textView);
    }

    //StartAnim
    private void startAnim() {

        Animation fadeIn = AnimationUtils.loadAnimation(SplashActivity.this,
                R.anim.fade_in);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Intent intent  = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        textView.startAnimation(fadeIn);

    }
}
