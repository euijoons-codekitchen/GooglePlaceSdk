package com.example.googleplayssdkprj.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static Retrofit retrofit;

    private RetrofitClient() {


    }
    public static Retrofit getRetrofit(){
        synchronized (RetrofitClient.class){
            if(retrofit == null){
                retrofit = new Retrofit.Builder()
                        .baseUrl("https://maps.googleapis.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                return retrofit;
            }else
                return retrofit;
        }
    }

}
