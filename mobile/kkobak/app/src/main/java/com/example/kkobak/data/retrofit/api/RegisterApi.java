package com.example.kkobak.data.retrofit.api;

import com.example.kkobak.data.retrofit.service.RegisterService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterApi extends BaseApi {

    public static RegisterService doRegistService() {
        return (getInstance().create(RegisterService.class));
    }

}
