package com.example.kkobak.repository.util;

import com.example.kkobak.repository.request.GPSRequest;
import com.example.kkobak.repository.request.HeartRequest;
import com.example.kkobak.repository.request.LoginRequest;
import com.example.kkobak.repository.response.TodoListResponse;
import com.example.kkobak.repository.response.TokenResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("member/login")
    Call<TokenResponse> login(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @GET("member/watch/today-list")
    Call<List<TodoListResponse>> getTodoList(@Header("Authorization") String authorization);

    @POST("heart/list")
    Call<Boolean> sendHeartList(@Body List<Integer> list);

    @POST("bpm/add")
    Call<Boolean> sendHeartOne(@Body HeartRequest heartrequest, @Header("Authorization") String authorization);

    @POST("gps/add")
    Call<Boolean> sendGPSOne(@Body GPSRequest gpsRequest, @Header("Authorization") String authorization);

}
