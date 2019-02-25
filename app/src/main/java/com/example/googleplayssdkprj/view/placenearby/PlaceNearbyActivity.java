package com.example.googleplayssdkprj.view.placenearby;

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

import com.example.googleplayssdkprj.R;
import com.example.googleplayssdkprj.dto.KTLocation;
import com.example.googleplayssdkprj.presenter.PlaceNearbyPresenter;
import com.example.googleplayssdkprj.view.findbyaddress.OnLocationReadyView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceNearbyActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnLocationReadyView, AdapterView.OnItemSelectedListener {

    private String TAG = PlaceNearbyActivity.class.getName();

    @BindView(R.id.edit_placenearby)
    EditText mEditText;
    @BindView(R.id.btn_placenearby)
    Button button;
    @BindView(R.id.spinner_placenearby)
    Spinner mSpinner;


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
        button.setOnClickListener((v)->{


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
        mGoogleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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
    public void drawmap(KTLocation ktLocation) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mGoogleApiClient.connect();
        mMapView.onStart();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

    }
}
