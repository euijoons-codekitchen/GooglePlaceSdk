package com.example.googleplayssdkprj.view.currentposition;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.googleplayssdkprj.R;
import com.example.googleplayssdkprj.dto.KTLocation;
import com.example.googleplayssdkprj.dto.MainItemViewModel;
import com.example.googleplayssdkprj.helper.CurrentLocationManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CurrentPositionActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int PLACE_PICKER_REQUEST = 10;
    private String TAG = CurrentPositionActivity.class.getName();
    private PlacesClient mPlacesClient;


    private GoogleMap mGoogleMap;

    private List<Address> address;
    private GoogleApiClient mGoogleApiClient;
    MapView mapView;
    CurrentLocationManager currentLocationManager;
    //
//    @BindView(R.id.map)
//    MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");
        ButterKnife.bind(this);
        setContentView(R.layout.activity_current_position);

        currentLocationManager = new CurrentLocationManager(this,this);
        readyMap(savedInstanceState);
        mGoogleApiClient = currentLocationManager.getmGoogleApiClient();
    }

    public void readyMap(Bundle savedInstanceState){
        mapView = (MapView) findViewById(R.id.map);
        if(mapView!=null)
            mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: ");
        mGoogleMap = googleMap;

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        mGoogleApiClient.connect();
        mapView.onStart();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected: ");
        mapView.onStart();
        KTLocation ktLocation = currentLocationManager.getCurrentLocation();
        MainItemViewModel.getCurrentAddress().setValue(ktLocation.getFormatted_address());
        setMarkerWithCoordinates(ktLocation.getLat(),ktLocation.getLon(),ktLocation.getFormatted_address());

    }
    public void setMarkerWithCoordinates(Double lat, Double lan, String currentAddress){
        LatLng currentLocation = new LatLng(lat,lan);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title(currentAddress);
        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        Toast.makeText(getApplicationContext(), currentAddress, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: ");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: ");
    }
  
}
