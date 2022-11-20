package com.a104.kkobak.data.retrofit.service;

import com.a104.kkobak.data.retrofit.model.LogChkReq;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LogChkService {
    @POST("log/change-status")
    Call<Boolean> isValid(@Header("Authorization") String authorization, @Body LogChkReq logChkReqC);
}
