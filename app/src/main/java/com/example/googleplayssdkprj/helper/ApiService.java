package com.example.googleplayssdkprj.helper;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/maps/api/geocode/json")
    Call<HashMap<String,Object>> findByAddress(@Query(value="address")String address, @Query(value="key") String apikey);
}
