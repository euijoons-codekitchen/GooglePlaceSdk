package com.example.googleplayssdkprj.view.placenearby;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googleplayssdkprj.GlobalApplication;
import com.example.googleplayssdkprj.R;
import com.example.googleplayssdkprj.dto.KTLocation;
import com.example.googleplayssdkprj.dto.KTStore;
import com.example.googleplayssdkprj.dto.MainItemViewModel;
import com.example.googleplayssdkprj.helper.CurrentLocationManager;
import com.example.googleplayssdkprj.presenter.PlaceNearbyPresenter;
import com.example.googleplayssdkprj.view.SpecificPlaceActivity;
import com.example.googleplayssdkprj.view.findbyaddress.FindByAddressActivity;
import com.example.googleplayssdkprj.view.findbyaddress.OnLocationReadyView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceNearbyActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnLocationReadyView, AdapterView.OnItemSelectedListener
        {

    private String TAG = PlaceNearbyActivity.class.getName();
    @BindView(R.id.tv_middle_placenearby)
    TextView textView;
    @BindView(R.id.edit_placenearby)
    EditText mEditText;
    @BindView(R.id.btn_placenearby)
    Button mButtonFindAddress;
    @BindView(R.id.btn_placenearby_findnearby)
    Button mButtonFindNearby;
    @BindView(R.id.spinner_placenearby)
    Spinner mSpinner;

    HashMap<String, KTStore> storeMap;
    KTLocation ktLocation;
    CurrentLocationManager manager;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private PlaceNearbyPresenter presenter;
    private GoogleApiClient mGoogleApiClient;
    private ArrayAdapter<CharSequence> adapter;

    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palce_nearby);
        ButterKnife.bind(this);
        //https://developers.google.com/places/web-service/search?hl=ko
        //https://developers.google.com/places/web-service/supported_types?hl=ko#table2
        //현위치 마커 찍고 그 근처 선태한 분류(레스토랑, 공항, 은행등) 마커 찍기
        //그 마커 찍으면 그 마커에 해당하는 상세정보 뷰 액티비티 보여주기
        //개꿀잼
        setPresenter(this);
        setmGoogleMap(savedInstanceState);
        setSpinner();

        storeMap = new HashMap<>();
        mButtonFindAddress.setOnClickListener((v)->{
            presenter.getCurrentLocationFromServer(mEditText.getText().toString());

        });
        mButtonFindNearby.setOnClickListener((v)->{
            //presenter.getNearbyInfoFromServer();
            mGoogleMap.clear();
            storeMap.clear();
            //getDefaultLocationAndDrawMarker();
            presenter.getCurrentLocationFromServer(mEditText.getText().toString());
            presenter.getNearbyInfoFromServer(ktLocation,type, GlobalApplication.getApiKey(),"");
        });
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemSelected: position "+position);
        type = adapter.getItem(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "onNothingSelected: ");
    }

    private void setSpinner() {
        adapter = ArrayAdapter.createFromResource(
                this,R.array.type_array,android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.getItem(0).toString();
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    private void setPresenter(OnLocationReadyView view) {
        presenter = new PlaceNearbyPresenter(view);
    }


    public void setmGoogleMap(Bundle savedInstanceState){
        mMapView = (MapView) findViewById(R.id.map_placenearby);
        if (mMapView != null)
            mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        manager = new CurrentLocationManager(this,this);
        mGoogleApiClient = manager.getmGoogleApiClient();

    }


    @Override
    public void drawMarkers(List<KTStore> stores) {

        for(int i=0;i<stores.size();i++){
            KTStore store = stores.get(i);
            KTLocation location = stores.get(i).getLocation();
            LatLng currentLocation = new LatLng(location.getLat(), location.getLon());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.snippet(String.valueOf(i));
            markerOptions.title(stores.get(i).getName());
            storeMap.put(String.valueOf(i),store);
            mGoogleMap.addMarker(markerOptions);
//            Log.d(TAG, "drawMarkers() called with: stores = [" + stores + "]");
//            Log.d(TAG, "drawMarkers() called with: location = [" + location + "]");

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        mMapView.onStart();
    }

    @Override
    public void drawMarker(KTLocation ktLocation) {
        textView.setText(ktLocation.getFormatted_address()+" "+ktLocation.getLat()+" "+ktLocation.getLon());
        this.ktLocation = ktLocation;
        LatLng currentLocation = new LatLng(ktLocation.getLat(), ktLocation.getLon());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title(ktLocation.getFormatted_address());

        Marker marker;

        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));


        Toast.makeText(getApplicationContext(), ktLocation.getFormatted_address(), Toast.LENGTH_SHORT).show();
    }

    public void getDefaultLocationAndDrawMarker(){
        ktLocation =manager.getCurrentLocation();
        setMarkerWithCoordinates(ktLocation.getLat(),ktLocation.getLon(),ktLocation.getFormatted_address());
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mGoogleApiClient.connect();
        mMapView.onStart();
        getDefaultLocationAndDrawMarker();

    }
    public void setMarkerWithCoordinates(Double lat, Double lan, String currentAddress){
        LatLng currentLocation = new LatLng(lat,lan);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title(currentAddress);

        textView.setText(currentAddress);
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
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String i = marker.getSnippet();
                KTStore store = storeMap.get(i);
                Log.d(TAG, "onInfoWindowClick: "+store.getName());
                MainItemViewModel.getKTStoreLiveData().setKtStore(store);
                startActivity(new Intent(GlobalApplication.getGlobalApplicationContext(), SpecificPlaceActivity.class));
            }
        });
    }


}
