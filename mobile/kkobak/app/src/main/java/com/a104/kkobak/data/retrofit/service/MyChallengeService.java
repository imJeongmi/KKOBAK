package com.a104.kkobak.data.retrofit.service;

import com.a104.kkobak.data.retrofit.model.MyChallengeRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface MyChallengeService {
    @Headers("Content-Type: application/json")
    @GET("member/my-chl-list?page=0&size=10000&sort=id,DESC")
    Call<List<MyChallengeRes>> getMyChallenge(
            @Header("Authorization") String authorization
    );
}
