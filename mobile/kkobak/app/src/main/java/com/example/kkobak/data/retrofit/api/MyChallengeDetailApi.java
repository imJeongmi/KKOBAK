package com.example.kkobak.data.retrofit.api;

import com.example.kkobak.data.retrofit.service.MyChallengeDetailService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyChallengeDetailApi {
    private static final String BASE_URL = "https://kkobak.ml/api/";

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        return (new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build());
    }

    public static MyChallengeDetailService getDetailInfo() {
        return (getInstance().create(MyChallengeDetailService.class));
    }
}
