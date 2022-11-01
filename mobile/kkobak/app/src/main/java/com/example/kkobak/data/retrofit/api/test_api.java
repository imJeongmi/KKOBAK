package com.example.kkobak.data.retrofit.api;

import com.example.kkobak.data.retrofit.service.test_interface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class test_api {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";


    public static test_interface getApiService(){return getInstance().create(test_interface.class);}

    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
