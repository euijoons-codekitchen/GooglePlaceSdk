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
import com.example.googleplayssdkprj.dto.MainItemViewModel;
import com.example.googleplayssdkprj.helper.onBackButtonClickedListener;
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

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CurrentPositionActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int PLACE_PICKER_REQUEST = 10;
    private String TAG = CurrentPositionActivity.class.getName();
    private PlacesClient mPlacesClient;
    private Location mLocation;

    private GoogleMap mGoogleMap;
    private MapView mapView;
    private List<Address> address;
    private String currentAddress;
    private Disposable disposable;
    onBackButtonClickedListener listener;
    private GoogleApiClient mGoogleApiClient;

    MainItemViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");
        ButterKnife.bind(this);
        setContentView(R.layout.activity_current_position);

        model = ViewModelProviders.of(this).get(MainItemViewModel.class);

        mapView = (MapView) findViewById(R.id.map);
        if(mapView!=null)
            mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        try {
            address = geocoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 3);
            //String currentLocationAddress = address.get(0).getAddressLine(0).toString();
            Address addr = address.get(0);
            currentAddress = addr.getCountryName() + " " + addr.getPostalCode() + " " + addr.getLocality() + " "
                    + addr.getThoroughfare() + " "
                    + addr.getFeatureName();


            //model.getMainItemLiveData().setAddress(currentAddress);
            Log.d(TAG, "onConnected: "+model.getMainItemLiveData().getAddress());
            model.getMainItemLiveData().setValue(currentAddress);//여기선 들어가는데..

            //Log.d(TAG, "onConnected: "+model.getMainItemLiveData().getValue());


            LatLng currentLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(currentAddress);
            mGoogleMap.addMarker(markerOptions);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
            Toast.makeText(getApplicationContext(), currentAddress, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    //Place API
    public void permissionConfiguration(){
        List<Place.Field> fields = Arrays.asList(Place.Field.NAME);
        FindCurrentPlaceRequest findCurrentPlaceRequest
                = FindCurrentPlaceRequest.builder(fields).build();

        if(ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            Task<FindCurrentPlaceResponse> placeResponses = mPlacesClient.findCurrentPlace(findCurrentPlaceRequest);
            placeResponses.addOnCompleteListener(task -> {
                if( task.isSuccessful()){
                    //loadGoogleMap();
                    FindCurrentPlaceResponse response = task.getResult();
                    Log.d(TAG, "onCreate: onSuccessful");
                    for(PlaceLikelihood likelihood : response.getPlaceLikelihoods()){
                        Log.d(TAG, "onCreate: "+likelihood.getPlace().getName());
                        Log.d(TAG, "onCreate: "+likelihood.getPlace().getAddress());
                    }

                }else{
                    Exception exception = task.getException();
                    if(exception instanceof ApiException){
                        ApiException exception1 = (ApiException) exception;
                        Log.d(TAG, "onCreate: " + exception1);
                    }
                }
            });
        }else{
            //getCurrentLocationPermission();
        }
    }


}
