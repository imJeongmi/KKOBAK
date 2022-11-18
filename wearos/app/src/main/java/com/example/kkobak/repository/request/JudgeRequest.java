package com.example.kkobak.repository.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class JudgeRequest {
    @SerializedName("chlId") private Long chlId;
    @SerializedName("startTime") private String startTime;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;

    public JudgeRequest(Long chlId, String startTime, String lat, String lng) {
        this.chlId = chlId;
        this.startTime = startTime;
        this.lat = lat;
        this.lng = lng;
    }
}
