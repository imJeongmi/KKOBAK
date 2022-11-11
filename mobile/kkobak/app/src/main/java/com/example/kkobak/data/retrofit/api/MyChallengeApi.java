package com.example.kkobak.data.retrofit.api;

import com.example.kkobak.data.retrofit.service.MyChallengeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyChallengeApi extends BaseApi {
    public static MyChallengeService getMyChallengeService() {
        return (getInstance().create(MyChallengeService.class));
    }
}
