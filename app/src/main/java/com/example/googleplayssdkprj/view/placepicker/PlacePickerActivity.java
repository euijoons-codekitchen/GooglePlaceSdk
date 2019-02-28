package com.example.googleplayssdkprj.view.placepicker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.googleplayssdkprj.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.libraries.places.api.Places;


import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class PlacePickerActivity extends AppCompatActivity  {

    String TAG = PlacePickerActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);
        setUpAutoCompleteFragment();
    }

    private void setUpAutoCompleteFragment() {
        AutocompleteSupportFragment autocompleteSupportFragment 
                = (AutocompleteSupportFragment)getSupportFragmentManager().
                findFragmentById(R.id.autocomplete_fragment_place_picker);
        
        List<Place.Field> arrays = Arrays.asList(Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS);
        
        autocompleteSupportFragment.setPlaceFields(arrays);
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.d(TAG, "onPlaceSelected: ");
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d(TAG, "onError: ");
            }
        });
    }


}
