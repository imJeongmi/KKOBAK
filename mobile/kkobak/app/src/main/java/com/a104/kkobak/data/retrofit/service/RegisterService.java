package com.a104.kkobak.data.retrofit.service;

import com.a104.kkobak.data.retrofit.model.RegisterReq;
import com.a104.kkobak.data.retrofit.model.RegisterRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("member/join")
    Call<RegisterRes> doRegist(@Body RegisterReq registerReq);
}
