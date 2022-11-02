package com.example.kkobak.repository.response;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class TokenResponse {
    @SerializedName("Authorization") private String accessToken;

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
