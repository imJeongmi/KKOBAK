package com.a104.kkobak.data.retrofit.service;

import com.a104.kkobak.data.retrofit.model.test_model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface test_interface {
    @GET("posts/{UserID}")
    Call<test_model> test_api_get(
            @Path("UserID") String userid);
}

