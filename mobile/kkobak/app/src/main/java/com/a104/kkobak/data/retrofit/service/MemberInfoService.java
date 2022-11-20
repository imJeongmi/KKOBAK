package com.a104.kkobak.data.retrofit.service;

import com.a104.kkobak.data.retrofit.model.MemberInfoRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface MemberInfoService {
    @GET("member/my-info")
    Call<MemberInfoRes> getInfo(@Header("Authorization") String authorization);
}
