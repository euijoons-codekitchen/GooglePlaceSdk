package com.example.googleplayssdkprj.presenter;

import com.example.googleplayssdkprj.helper.ApiService;
import com.example.googleplayssdkprj.helper.RetrofitClient;
import com.example.googleplayssdkprj.view.findbyaddress.OnLocationReadyView;

import java.util.HashMap;

import retrofit2.Call;

public class PlaceNearbyPresenter {

    private final int Radius = 1500;
    private OnLocationReadyView view;

    public PlaceNearbyPresenter(OnLocationReadyView view) {
        this.view = view;

    }

    public void getInfoFromServer(String location, String keyword, String type){

        ApiService service = (ApiService) RetrofitClient.getRetrofit().create(ApiService.class);

    }
}
