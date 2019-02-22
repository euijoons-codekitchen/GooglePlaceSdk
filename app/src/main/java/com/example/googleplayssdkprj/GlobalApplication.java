package com.example.googleplayssdkprj;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.google.android.libraries.places.api.*;
import com.google.android.libraries.places.api.net.PlacesClient;


public class GlobalApplication extends Application {

    private static final String TAG = GlobalApplication.class.getName();
    public static GlobalApplication obj;

    private static PlacesClient placesClient;

    @Override
    public void onCreate() {
        super.onCreate();
        obj = this;
        Places.initialize(this, GlobalApplication.getApiKey());
        placesClient = Places.createClient(this);

    }

    public static PlacesClient getPlaceClient(){
        return placesClient;
    }

    public static String getApiKey(){
        //return getGlobalApplicationContext().getResources().getString(R.string.google_places_api);
        return getGlobalApplicationContext().getResources().getString(R.string.google_maps_api);
    }

    public static GlobalApplication getGlobalApplicationContext(){

        return obj;
    }



}
