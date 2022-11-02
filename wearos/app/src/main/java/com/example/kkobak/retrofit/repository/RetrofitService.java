package com.example.kkobak.retrofit.repository;

import com.example.kkobak.retrofit.repository.request.LoginRequest;
import com.example.kkobak.retrofit.repository.response.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {

    @POST("/member/login")
    Call<TokenResponse> login(@Body LoginRequest loginRequest);
}
