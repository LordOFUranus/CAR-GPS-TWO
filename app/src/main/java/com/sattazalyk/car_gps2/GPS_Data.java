package com.sattazalyk.car_gps2;

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class GPS_Data extends Application {

    private static GPS_Data singleton;

    private List<Location> locationPoints;

    public List<Location> getLocationPoints() {
        return locationPoints;
    }

    public void setLocationPoints(List<Location> locationPoints) {
        this.locationPoints = locationPoints;
    }

    public GPS_Data getInstance(){
        return singleton;
    }

    public void onCreate(){
        super.onCreate();
        singleton = this;
        locationPoints = new ArrayList<>();
    }
}
