package com.example.googleplayssdkprj.dto;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class MainItemLiveData extends MutableLiveData<String> {

    private String Address;
    private String TAG = MainItemLiveData.class.getName();

    public MainItemLiveData() {

        Log.d(TAG, "MainItemLiveData: ");
    }
    @Override
    protected void onActive() {
        super.onActive();
        Address = getValue();
        Log.d(TAG, "onActive: "+Address);
    }


    @Override
    protected void onInactive() {
        super.onInactive();

        Log.d(TAG, "onInactive: ");
    }
}
