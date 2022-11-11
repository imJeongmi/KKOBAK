package com.example.kkobak.data.retrofit.api;

import com.example.kkobak.data.retrofit.service.GpsDataService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GpsDataApi extends BaseApi {

    public static GpsDataService sendGpsData() {
        return (getInstance().create(GpsDataService.class));
    }
}
