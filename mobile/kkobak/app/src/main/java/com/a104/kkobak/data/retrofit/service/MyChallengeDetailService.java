package com.a104.kkobak.data.retrofit.service;

import com.a104.kkobak.data.retrofit.model.MyChallengeDetailRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface MyChallengeDetailService {
    @Headers("Content-Type: application/json")
    @GET("challenge/{challengeId}")
    Call<MyChallengeDetailRes> getInfo(
            @Header("Authorization") String authorization,
            @Path("challengeId") String challengeId
    );
}
