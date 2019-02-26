package com.example.googleplayssdkprj.dto;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

public class KTStoreLiveData extends MutableLiveData<KTStore> {

    private KTStore ktStore;
    private String TAG = KTStoreLiveData.class.getName();

    public KTStore getKtStore() {
        return ktStore;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive: ");
        super.onActive();
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive: ");
        super.onInactive();
    }

    public void setKtStore(KTStore ktStore) {
        this.ktStore = ktStore;
    }
}
