package com.example.kkobak.data.retrofit.service;

import com.example.kkobak.data.retrofit.model.CallengeChkRes;
import com.example.kkobak.data.retrofit.model.ChallengeAllDataRes;
import com.example.kkobak.data.retrofit.model.GpsDataReq;
import com.example.kkobak.data.retrofit.model.JudgeReq;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChallengeService {
    @GET("challenge/chk/{cid}")
    Call<CallengeChkRes> getStatusInfo(
            @Header("Authorization") String authorization,
            @Path("cid") int cid
    );

    @GET("challenge/change/{cid}/{cnt}")
    Call<Boolean> setCnt(
            @Header("Authorization") String authorization,
            @Path("cid") int cid,
            @Path("cnt") int cnt
    );

    @GET("challenge/done/{cid}")
    Call<Boolean> getFinStatus(
            @Header("Authorization") String authorization,
            @Path("cid") int cid
    );

    @GET("challenge/app-list")
    Call<List<ChallengeAllDataRes>> getAllData(
            @Header("Authorization") String authorization
    );

    @POST("challenge/judge")
    Call<Boolean> judgeChallenge(
            @Header("Authorization") String authorization,
            @Body JudgeReq JudgeReq
    );
}
