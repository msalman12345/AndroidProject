package com.test.salman.androidassessment.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.salman.androidassessment.R;
import com.test.salman.androidassessment.activities.MainActivity;
import com.test.salman.androidassessment.entities.NearByRestaurantResponse.Result;
import com.test.salman.androidassessment.entities.WeatherResponse.DailyData;
import com.test.salman.androidassessment.helper.helpers.APPHelper;
import com.test.salman.androidassessment.helper.helpers.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salman on 3/2/2019.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {


    List<Result> list;
    Context context;
    double rating;
    String locality;
    String photoRefrence;
    String photoUrl;


    public RestaurantAdapter(Context context) {
        this.list = new ArrayList<>();;
        this.context = context;
        locality = PreferenceHelper.getInstance(context).getPlaceLocality();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_row_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result resultData = list.get(position);

        holder.restName.setText(APPHelper.isStringEmptyOrNull(resultData.getName()));
        holder.restLoc.setText(APPHelper.isStringEmptyOrNull(locality));
        holder.restSpeciality.setText("Food");
        holder.restReviews.setText(String.valueOf(APPHelper.isIntegerNull(resultData.getUserRatingsTotal())));
        rating = resultData.getRating();

        try {
            if (resultData.getPhotos() != null) {
                photoRefrence = resultData.getPhotos().get(0).getPhotoReference();
                photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference=" + photoRefrence +
                        "&key=" + context.getResources().getString(R.string.API_KEY_TWO);
                Picasso.with(context).load(photoUrl).placeholder(R.drawable.ic_launcher_background).into(holder.restIv);
                Log.v("PhotoRefrence", photoRefrence + "");
                Log.v("photoUrl", photoUrl + "");
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        try{
            holder.rating.setText(String.valueOf(resultData.getRating()));
            holder.restRatingBar.setRating(Float.valueOf(String.valueOf(rating)));

        }catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        Picasso.with(context).load(resultData.getIcon()).placeholder(R.drawable.ic_launcher_background).into(holder.dishIv);


    }

    public void addAll(List<Result> list_) {
        list.clear();
        list.addAll(list_);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView restName,rating,restLoc,restSpeciality,restReviews;
        public RatingBar restRatingBar;
        public ImageView restIv,dishIv;

        public ViewHolder(View view) {
            super(view);
            restName = (TextView) view.findViewById(R.id.restaurant_name_tv);
            rating = (TextView) view.findViewById(R.id.rating_tv);
            restLoc = (TextView) view.findViewById(R.id.loc_tv);
            restSpeciality = (TextView) view.findViewById(R.id.restaurant_speciality);
            restRatingBar = (RatingBar) view.findViewById(R.id.rating);
            restReviews = (TextView) view.findViewById(R.id.reviews);
            restIv = (ImageView) view.findViewById(R.id.restaurant_iv);
            dishIv = (ImageView) view.findViewById(R.id.dish_iv);
        }
    }
}
