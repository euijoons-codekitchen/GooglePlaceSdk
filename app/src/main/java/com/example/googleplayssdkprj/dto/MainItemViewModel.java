package com.example.googleplayssdkprj.dto;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

public class MainItemViewModel extends ViewModel {
    private static MainItemLiveData currentAddress;
    private static MainItemLiveData foundAddress;
    private static MainItemLiveData placeNearbyAddress;
    //private static MainItemLiveData add
    static String TAG = MainItemViewModel.class.getName();


    public static MainItemLiveData getFoundAddress() {
        if(foundAddress == null){
            foundAddress = new MainItemLiveData();
            Log.d(TAG, "getFoundAddress: ");
        }
        return foundAddress;
    }

    public static MainItemLiveData getCurrentAddress(){
        if(currentAddress == null) {
            currentAddress = new MainItemLiveData();
            Log.d(TAG, "getCurrentAddress: created");
        }

        return currentAddress;
    }

    public static MainItemLiveData getPlaceNearbyAddress(){
        if(placeNearbyAddress == null){
            placeNearbyAddress = new MainItemLiveData();
        }
        return placeNearbyAddress;
    }
}
