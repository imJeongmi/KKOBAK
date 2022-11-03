package com.example.kkobak.data.retrofit.service;

import com.example.kkobak.data.retrofit.model.MyChallengeRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface MyChallengeService {
    @Headers("Content-Type: application/json")
    @GET
    Call<MyChallengeRes> getMyChallenge(
            @Header("Authorization") String authorization
    );
}
