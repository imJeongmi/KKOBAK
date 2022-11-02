package com.example.kkobak.data.retrofit.api;

import com.example.kkobak.data.retrofit.service.LoginService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginApi {
    private static final String BASE_URL = "https://kkobak.ml/api/";

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        return (new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build());
    }

    public static LoginService doLoginService() {
        return (getInstance().create(LoginService.class));
    }
}
