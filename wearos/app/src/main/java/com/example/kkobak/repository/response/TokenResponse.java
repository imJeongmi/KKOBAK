package com.example.kkobak.repository.response;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class TokenResponse {
    @SerializedName("accessToken") private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
