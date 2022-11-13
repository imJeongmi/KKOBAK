package com.example.kkobak.data.retrofit.service;

import com.example.kkobak.data.retrofit.model.CallengeChkRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
}
