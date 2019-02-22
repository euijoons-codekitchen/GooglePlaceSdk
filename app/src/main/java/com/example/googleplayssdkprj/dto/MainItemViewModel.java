package com.example.googleplayssdkprj.dto;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

public class MainItemViewModel extends ViewModel {
    private static MainItemLiveData mainItemLiveData;
    static String TAG = MainItemViewModel.class.getName();



    public static MainItemLiveData getMainItemLiveData(){
        if(mainItemLiveData == null) {
            mainItemLiveData = new MainItemLiveData();
            Log.d(TAG, "getMainItemLiveData: created");
        }

        return mainItemLiveData;
    }
}
