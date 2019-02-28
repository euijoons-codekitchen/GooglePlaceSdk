package com.example.googleplayssdkprj.view.findbyaddress;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googleplayssdkprj.R;
import com.example.googleplayssdkprj.dto.KTLocation;
import com.example.googleplayssdkprj.dto.KTStore;
import com.example.googleplayssdkprj.dto.MainItemViewModel;
import com.example.googleplayssdkprj.helper.CurrentLocationManager;
import com.example.googleplayssdkprj.presenter.FindByAddressPresenter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindByAddressActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnLocationReadyView{


    private String TAG = FindByAddressActivity.class.getName();

    private GoogleMap mGoogleMap;
    MapView mapView;

    @BindView(R.id.edit_findbyaddress)
    EditText editText;
    @BindView(R.id.btn_findbyaddress)
    Button button;
    @BindView(R.id.tv_middle_findbyaddress)
    TextView textView;

    private CurrentLocationManager manager;
    private FindByAddressPresenter presenter;
    private  GoogleApiClient mGoogleApiClient;

    //입력한 주소를 바탕으로 서버 요청 보내서 주소 정보 가져오기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findbyaddress);
        presenter = new FindByAddressPresenter(this);
        ButterKnife.bind(this);

        setmGoogleMap(savedInstanceState);

        button.setOnClickListener((v)->{
            Log.d(TAG, "setOnclickListner: " + editText.getText().toString());
            presenter.getCurrentLocationFromServer(editText.getText().toString());
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
    public void drawMarker(KTLocation ktLocation) {
        Log.d(TAG, "drawMarker: ");

        textView.setText(ktLocation.getFormatted_address()+" "+ktLocation.getLat()+" "+ktLocation.getLon());

        LatLng currentLocation = new LatLng(ktLocation.getLat(), ktLocation.getLon());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title(ktLocation.getFormatted_address());

        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        Toast.makeText(getApplicationContext(), ktLocation.getFormatted_address(), Toast.LENGTH_SHORT).show();
    }

    public void setmGoogleMap(Bundle savedInstanceState) {

        mapView = (MapView) findViewById(R.id.map_findbyaddress);
        if (mapView != null)
            mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        manager = new CurrentLocationManager(this,this);
        mGoogleApiClient = manager.getmGoogleApiClient();


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mGoogleApiClient.connect();
        mapView.onStart();
        KTLocation ktLocation = manager.getCurrentLocation();
        setMarkerWithCoordinates(ktLocation.getLat(),ktLocation.getLon(),ktLocation.getFormatted_address());

    }

    public void setMarkerWithCoordinates(Double lat, Double lan, String currentAddress){
        LatLng currentLocation = new LatLng(lat,lan);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title(currentAddress);

        MainItemViewModel.getFoundAddress().setValue(currentAddress);

        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        Toast.makeText(getApplicationContext(), currentAddress, Toast.LENGTH_SHORT).show();
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

    @Override
    public void drawMarkers(List<KTStore> stores) {

    }
}
