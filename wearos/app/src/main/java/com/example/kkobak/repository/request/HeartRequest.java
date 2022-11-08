package com.example.kkobak.repository.request;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeartRequest {
    @SerializedName("chlId") private Long chlId;
    @SerializedName("time") private String time;
    @SerializedName("bpm") private int bpm;
    @SerializedName("chk")private String chk;

    public HeartRequest(long chlId, String time, int bpm, String chk){
        this.chlId = chlId;
        this.time = time;
        this.bpm = bpm;
        this.chk = chk;
    }
}