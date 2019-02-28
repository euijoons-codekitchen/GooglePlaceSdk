package com.example.googleplayssdkprj.helper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.example.googleplayssdkprj.GlobalApplication;
import com.example.googleplayssdkprj.dto.KTLocation;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CurrentLocationManager {


    private GoogleApiClient mGoogleApiClient;
    //apiclient list 만들기..?

    public CurrentLocationManager(GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener failedListener) {
        mGoogleApiClient = new GoogleApiClient.Builder(GlobalApplication.getGlobalApplicationContext()).
                addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(failedListener)
                .addApi(LocationServices.API)
                .build();

    }

    public KTLocation getCurrentLocation(){ // 서버 요청 없이 안드로이드 내부 함수로 현위치 검색
        KTLocation ktLocation = new KTLocation();
        if (ActivityCompat.checkSelfPermission(GlobalApplication.getGlobalApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GlobalApplication.getGlobalApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        Geocoder geocoder = new Geocoder(GlobalApplication.getGlobalApplicationContext(), Locale.KOREA);
        try {

            List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 3);
            //String currentLocationAddress = address.get(0).getAddressLine(0).toString();
            Address addr = address.get(0);
            String currentAddress = addr.getCountryName() + " " + addr.getPostalCode() + " " + addr.getLocality() + " "
                    + addr.getThoroughfare() + " "
                    + addr.getFeatureName();

            ktLocation.setFormatted_address(currentAddress);
            ktLocation.setLat(location.getLatitude());
            ktLocation.setLon(location.getLongitude());



        } catch (IOException e) {
            e.printStackTrace();
        }
        return ktLocation;
    }

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }

}
