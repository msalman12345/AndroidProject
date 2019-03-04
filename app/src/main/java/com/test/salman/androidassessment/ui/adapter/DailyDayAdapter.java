package com.test.salman.androidassessment.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.salman.androidassessment.R;
import com.test.salman.androidassessment.activities.MainActivity;
import com.test.salman.androidassessment.entities.WeatherResponse.DailyData;
import com.test.salman.androidassessment.helper.helpers.WeatherHelper;
import com.test.salman.androidassessment.helper.helpers.WeatherIcons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Salman on 3/3/2019.
 */

public class DailyDayAdapter extends RecyclerView.Adapter<DailyDayAdapter.ViewHolder> {

    List<DailyData> list;
    MainActivity context;
    HashMap<String,Drawable> weatherIcon;

    public DailyDayAdapter(MainActivity context) {
        this.list = new ArrayList<>();;
        this.context = context;
    }

    @Override
    public DailyDayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_day_row_item, parent, false);

        return new DailyDayAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DailyDayAdapter.ViewHolder holder, int position) {
        DailyData dailyData = list.get(position);

        Long dailyTemp = Math.round(dailyData.getTemperatureLow());
        String dailyTempLabel = String.format(Locale.ENGLISH, "%s°", dailyTemp);

        int dailyTime = dailyData.getTime();
        String dailyDayName = WeatherHelper.convertEpochToString(dailyTime,"EEEE",
                "GMT-6:00");
        String dailyDayLabel = String.format(Locale.ENGLISH, "%s", dailyDayName);

        weatherIcon = WeatherIcons.weatherIcons(context);

        if(weatherIcon.containsKey(dailyData.getIcon())){

            holder.dailyDayIcon.setImageDrawable(weatherIcon.get(dailyData.getIcon()));
        }

        holder.dailyDayName.setText(dailyDayName);
        holder.dailyDayTemp.setText(dailyTempLabel);

    }

    public void addAll(List<DailyData> list_) {
        list.clear();
        list.addAll(list_);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dailyDayName,dailyDayTemp;
        public ImageView dailyDayIcon;

        public ViewHolder(View view) {
            super(view);
            dailyDayName = (TextView) view.findViewById(R.id.daily_day);
            dailyDayIcon = (ImageView) view.findViewById(R.id.daily_day_icon);
            dailyDayTemp = (TextView) view.findViewById(R.id.daily_day_temp);
        }
    }
}

