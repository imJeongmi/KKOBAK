package com.a104.kkobak.data.retrofit.service;

import com.a104.kkobak.data.retrofit.model.LoginReq;
import com.a104.kkobak.data.retrofit.model.LoginRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("member/login")
    Call<LoginRes> doLogin(@Body LoginReq loginReq);
}
