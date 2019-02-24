package com.example.googleplayssdkprj.view.findbyaddress;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googleplayssdkprj.GlobalApplication;
import com.example.googleplayssdkprj.R;
import com.example.googleplayssdkprj.dto.KTLocation;
import com.example.googleplayssdkprj.dto.MainItemViewModel;
import com.example.googleplayssdkprj.helper.ApiService;
import com.example.googleplayssdkprj.presenter.FindByAddressPresenter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleMapWithAddressActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,FindByAddressView {


    private String TAG = GoogleMapWithAddressActivity.class.getName();
    private Location mLocation;

    private GoogleMap mGoogleMap;
    MapView mapView;

    @BindView(R.id.edit_findbyaddress)
    EditText editText;
    @BindView(R.id.btn_findbyaddress)
    Button button;
    @BindView(R.id.tv_middle)
    TextView textView;

    private MainItemViewModel model;
    private GoogleApiClient mGoogleApiClient;
    private FindByAddressPresenter presenter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_with_address);
        presenter = new FindByAddressPresenter(this);
        ButterKnife.bind(this);

        setmGoogleMap(savedInstanceState);
        button.setOnClickListener((v)->{
            Log.d(TAG, "setOnclickListner: " + editText.getText().toString());
            presenter.getInfoFromServer(editText.getText().toString());

        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void drawmap(KTLocation ktLocation) {
        Log.d(TAG, "drawmap: ");

        textView.setText(ktLocation.getFormatted_address()+" "+ktLocation.getLat()+" "+ktLocation.getLng());

        LatLng currentLocation = new LatLng(ktLocation.getLat(), ktLocation.getLng());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title(ktLocation.getFormatted_address());
        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        Toast.makeText(getApplicationContext(), ktLocation.getFormatted_address(), Toast.LENGTH_SHORT).show();
    }

    public void setmGoogleMap(Bundle savedInstanceState){

        mapView = (MapView) findViewById(R.id.map_findbyaddress);


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
    public void onConnected(@Nullable Bundle bundle) {
        mGoogleApiClient.connect();
        mapView.onStart();


    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: ");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

    }

}
