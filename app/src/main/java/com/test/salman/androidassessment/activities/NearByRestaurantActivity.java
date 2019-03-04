package com.test.salman.androidassessment.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.test.salman.androidassessment.R;
import com.test.salman.androidassessment.constants.APIConstants;
import com.test.salman.androidassessment.entities.NearByRestaurantResponse.NearByRestWrapper;
import com.test.salman.androidassessment.entities.NearByRestaurantResponse.Result;
import com.test.salman.androidassessment.helper.helpers.APPHelper;
import com.test.salman.androidassessment.helper.helpers.PreferenceHelper;
import com.test.salman.androidassessment.helper.webhelper.WebRequest;
import com.test.salman.androidassessment.ui.adapter.RestaurantAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Salman on 3/3/2019.
 */

public class NearByRestaurantActivity extends AppCompatActivity{

    /*
    * Instance and local variables
    * */
    private static final String TAG = "RestaurantActivity";

    RecyclerView recyclerView;
    RestaurantAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    String latitude,longitude;
    Toolbar mToolbar;
    ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_layout);

        //Calling InitWidgets Method
        initWidgets();

        //Calling SetRestaurantData Method
        setRestaurantData();

        //Calling GetRestaurantApi
        getNearByRestaurant();


        //BackButton
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    //InitWidgets
    private void initWidgets(){
        latitude = PreferenceHelper.getInstance(this).getPlaceLatitude();
        longitude = PreferenceHelper.getInstance(this).getPlaceLongitude();
        recyclerView = (RecyclerView) findViewById(R.id.restaurant_rv);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.title));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        progressBar = (ProgressBar) findViewById(R.id.loader);
    }


    //setRestaurantData
    private void setRestaurantData(){
        adapter = new RestaurantAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    //API Of NearByRestaurant Based On Current Location
    private void getNearByRestaurant(){
        progressBar.setVisibility(View.VISIBLE);
        new WebRequest(this).getNearByRestaurant(APIConstants.SENSOR_, getResources().getString(R.string.API_KEY_TWO),
                APIConstants.TYPE_, latitude + "," + longitude, APIConstants.RADIUS_VALUE, new Callback<NearByRestWrapper>() {
                    @Override
                    public void onResponse(Call<NearByRestWrapper> call, Response<NearByRestWrapper> response) {
                        if(response.isSuccessful()){
                          if(response.body().getResults().size()>0) {
                              progressBar.setVisibility(View.INVISIBLE);
                              List<Result> swapList = APPHelper.swapListOfFiveStarRestaurant(response.body().getResults());
                              adapter.addAll(swapList);
                              adapter.notifyDataSetChanged();

                              Log.v("RestaurantName", response.body().getResults().get(0).getName() + "");
                          }

                        }else{
                            Log.v(TAG,"Result Size Is Empty");
                        }

                    }

                    @Override
                    public void onFailure(Call<NearByRestWrapper> call, Throwable t) {
                        Log.v(TAG,"onFailure : " + t.getMessage() );
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
