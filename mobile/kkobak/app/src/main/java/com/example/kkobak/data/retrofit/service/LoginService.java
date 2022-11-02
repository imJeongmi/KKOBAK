package com.example.kkobak.data.retrofit.service;

import com.example.kkobak.data.retrofit.model.LoginReq;
import com.example.kkobak.data.retrofit.model.LoginRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("member/login")
    Call<LoginRes> doLogin(@Body LoginReq loginReq);
}
