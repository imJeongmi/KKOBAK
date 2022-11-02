package com.example.kkobak.data.retrofit.service;

import com.example.kkobak.data.retrofit.model.RegisterReq;
import com.example.kkobak.data.retrofit.model.RegisterRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("member/join")
    Call<RegisterRes> doRegist(@Body RegisterReq registerReq);
}
