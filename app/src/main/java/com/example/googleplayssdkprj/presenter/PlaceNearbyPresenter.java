package com.example.googleplayssdkprj.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.googleplayssdkprj.GlobalApplication;
import com.example.googleplayssdkprj.dto.KTLocation;
import com.example.googleplayssdkprj.dto.KTStore;
import com.example.googleplayssdkprj.dto.MainItemViewModel;
import com.example.googleplayssdkprj.helper.ApiService;
import com.example.googleplayssdkprj.helper.RetrofitClient;
import com.example.googleplayssdkprj.view.findbyaddress.OnLocationReadyView;
import com.google.android.gms.location.places.Place;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlaceNearbyPresenter {

    private final int radius = 1500;
    private OnLocationReadyView view;
    private Retrofit retrofit;
    private String TAG = PlaceNearbyPresenter.class.getName();
    public PlaceNearbyPresenter(OnLocationReadyView view) {
        this.view = view;
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
                    //MainItemViewModel.getFoundAddress().setValue(formatted_address);
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

    public void getNearbyInfoFromServer(KTLocation ktLocation, String type, String apikey, String keyword){

        ApiService service = (ApiService) RetrofitClient.getRetrofit().create(ApiService.class);
        String parsedLocation = ktLocation.getLat()+","+ktLocation.getLon();
        Call<HashMap<String,Object>> c = service.nearbyAddress(parsedLocation,radius,type,keyword,apikey);
        c.enqueue(new Callback<HashMap<String, Object>>() {
            @Override
            public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {
                Log.d(TAG, "onResponse: ");
                if(!response.isSuccessful())
                    return;

                List<KTStore> stores = new ArrayList<>();

                HashMap<String,Object> body = response.body();
                //HashMap<String,Object> results = (HashMap<String, Object>) body.get("results");
                ArrayList<Object> results_array = (ArrayList<Object>) body.get("results");
                for(int i=0;i<results_array.size();i++){
                    LinkedTreeMap<String,Object> item = (LinkedTreeMap<String, Object>) results_array.get(i);
                    LinkedTreeMap<String,Object> geometry = (LinkedTreeMap<String, Object>) item.get("geometry");
                    LinkedTreeMap<String,Object> location = (LinkedTreeMap<String, Object>) geometry.get("location");
                    KTLocation ktLocation1 = new KTLocation();
                    ktLocation1.setLat((Double) location.get("lat"));
                    ktLocation1.setLon((Double) location.get("lng"));
                    ktLocation1.setFormatted_address((String) item.get("vicinity"));

                    KTStore store = new KTStore();
                    store.setLocation(ktLocation1);
                    store.setName((String) item.get("name"));

                    if(item.get("icon")!=null){
                        store.setIconUrl((String)item.get("icon"));
                    }

                    if(item.get("rating") != null)
                        store.setRating((Double) item.get("rating"));
                    if(item.get("photos") != null){
                        ArrayList<Object> photos = (ArrayList<Object>)item.get("photos");
                        LinkedTreeMap<String,Object> photoSpec = (LinkedTreeMap<String, Object>) photos.get(0);
                        store.setPhotoUrl((String) photoSpec.get("photo_reference"));
                        store.setPhotoWidth((Double)photoSpec.get("width"));
                        store.setPhotoHeight((Double)photoSpec.get("height"));
                    }
                    if(item.get("opening_hours") != null){
                        LinkedTreeMap<String,Object> opened = ((LinkedTreeMap<String, Object>) item.get("opening_hours"));
                        store.setOpened((Boolean) opened.get("open_now"));
                    }

                    stores.add(store);

                }
                view.drawMarkers(stores);
            }

            @Override
            public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                t.printStackTrace();

                Toast.makeText(GlobalApplication.getGlobalApplicationContext(),type+ " Not found Around Here",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
