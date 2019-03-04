package com.test.salman.androidassessment.entities.WeatherResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Salman on 3/2/2019.
 */

public class Flags {

    @SerializedName("sources")
    @Expose
    private List<String> sources = null;
    @SerializedName("nearest-station")
    @Expose
    private Double nearestStation;
    @SerializedName("units")
    @Expose
    private String units;

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public Double getNearestStation() {
        return nearestStation;
    }

    public void setNearestStation(Double nearestStation) {
        this.nearestStation = nearestStation;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
