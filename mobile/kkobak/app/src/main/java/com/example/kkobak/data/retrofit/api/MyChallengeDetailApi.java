package com.example.kkobak.data.retrofit.api;

import com.example.kkobak.data.retrofit.service.MyChallengeDetailService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyChallengeDetailApi extends BaseApi {

    public static MyChallengeDetailService getDetailInfo() {
        return (getInstance().create(MyChallengeDetailService.class));
    }
}
