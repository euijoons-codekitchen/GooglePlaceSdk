package com.example.googleplayssdkprj.view.mainview;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.googleplayssdkprj.R;
import com.example.googleplayssdkprj.dto.MainItem;
import com.example.googleplayssdkprj.dto.MainItemViewModel;
import com.example.googleplayssdkprj.presenter.MainPresenter;
import com.example.googleplayssdkprj.view.mainviewholder.MainViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    private String TAG = MainActivity.class.getName();
    MainPresenter mainPresenter;

    @BindView(R.id.recyclerview_main)
    RecyclerView mRecyclerView;

    MainViewAdapter adapter;

    MainItemViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setRecyclerViewConfig();
        setMainPresenterConfig();
        getCurrentLocationPermission();


        Log.d(TAG, "onCreate: ");
        MainItem item = adapter.getSpecificItem(0);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                MainItem item1 = adapter.getSpecificItem(0);

                item1.setDescription(s);
                adapter.updateSpecificItem(item1,0);
            }
        };
        model = ViewModelProviders.of(this).get(MainItemViewModel.class);
        MainItemViewModel.getMainItemLiveData().observe(this,observer);

    }


    public void setMainPresenterConfig(){
        mainPresenter = new MainPresenter(this);
        mainPresenter.setMainItems();

    }

    public void setRecyclerViewConfig(){

        adapter = new MainViewAdapter((index)->{
            mainPresenter.showMapsOnNextActivity(index);
            //have to remove
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecyclerView.setClickable(true);

        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void updateList(List<MainItem> itemList) {
        adapter.setItems(itemList);
    }


    private void getCurrentLocationPermission() {

        //어플리케이션 최초 설치 시 위치 정보 제공 동의
        String[] RequestList = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_WIFI_STATE};
        this.requestPermissions(RequestList,1);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
        ){
            Log.d(TAG, "getCurrentLocationPermission: " + "Permission Granted");
        }else
            Log.d(TAG, "getCurrentLocationPermission: " + "Permission Denied");
    }



}
