package com.example.kkobak.data.retrofit.service;

import com.example.kkobak.data.retrofit.model.test_model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface test_interface {
    @GET("posts/{UserID}")
    Call<test_model> test_api_get(
            @Path("UserID") String userid);
}

