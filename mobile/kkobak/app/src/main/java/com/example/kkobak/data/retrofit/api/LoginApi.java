package com.example.kkobak.data.retrofit.api;

import com.example.kkobak.data.retrofit.service.LoginService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginApi extends BaseApi {
    public static LoginService doLoginService() {
        return (getInstance().create(LoginService.class));
    }
}
