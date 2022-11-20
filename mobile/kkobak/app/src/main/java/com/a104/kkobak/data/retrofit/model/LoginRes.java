package com.a104.kkobak.data.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRes {
    @SerializedName("accessToken")
    @Expose
    String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
