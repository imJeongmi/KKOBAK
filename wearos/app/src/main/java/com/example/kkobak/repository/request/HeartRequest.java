package com.example.kkobak.repository.request;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeartRequest {
    @SerializedName("chlId") private Long chiId;
    @SerializedName("time") private LocalDateTime time;
    @SerializedName("bpm") private String bpm;
    @SerializedName("chk") private LocalDateTime chk;

    public GPSRequest(long chiId, LocalDateTime time, String lng, LocalDateTime chk){
        this.chiId = chiId;
        this.time = time;
        this.lat = lat;
        this.lng = lng;
        this.chk = chk;
    }
}