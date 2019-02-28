package com.example.googleplayssdkprj.presenter;

import android.util.Log;

import com.example.googleplayssdkprj.GlobalApplication;
import com.example.googleplayssdkprj.dto.KTLocation;
import com.example.googleplayssdkprj.dto.MainItemViewModel;
import com.example.googleplayssdkprj.helper.ApiService;
import com.example.googleplayssdkprj.helper.RetrofitClient;
import com.example.googleplayssdkprj.view.findbyaddress.OnLocationReadyView;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindByAddressPresenter {

    String TAG = FindByAddressPresenter.class.getName();
    private OnLocationReadyView view;
    private Retrofit retrofit;
    public FindByAddressPresenter(OnLocationReadyView view){
        this.view = view;
        setupRetrofit();
    }

    private void setupRetrofit() {
        retrofit = RetrofitClient.getRetrofit();
    }

    public void getCurrentLocationFromServer(String keyword){
        Log.d(TAG, "getCurrentLocationFromServer: "+keyword);
        ApiService service = (ApiService) retrofit.create(ApiService.class);
        Call<HashMap<String,Object>> c = service.findByAddress(keyword, GlobalApplication.getApiKey());
        c.enqueue(new Callback<HashMap<String, Object>>() {
            @Override
            public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {
                Log.d(TAG, "onResponse: ");
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+"response unsuccessful");
                }else{
                    HashMap<String,Object> obj = response.body();
                    ArrayList<Object> obj2 = (ArrayList<Object>) obj.get("results");

                    LinkedTreeMap<String,Object> results = (LinkedTreeMap<String,Object>)obj2.get(0);
                    String formatted_address = (String) results.get("formatted_address");
                    LinkedTreeMap<String,Object> geometry = (LinkedTreeMap<String,Object>) results.get("geometry");
                    LinkedTreeMap<String,Object> location = (LinkedTreeMap<String,Object>) geometry.get("location");
                    Double lat = (Double) location.get("lat");
                    Double lng = (Double) location.get("lng");
                    Log.d(TAG, "onResponse: formatted_address" +formatted_address);
                    Log.d(TAG, "onResponse: lat : " +lat);
                    Log.d(TAG, "onResponse: lng : "+lng);
                    MainItemViewModel.getFoundAddress().setValue(formatted_address);
                    view.drawMarker(new KTLocation(formatted_address,lat,lng));
                }

            }

            @Override
            public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                Request request = call.request();
                Log.d(TAG, "onFailure: ");
                t.printStackTrace();
            }
        });

    }


}
