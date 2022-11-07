package com.example.kkobak.repository.request;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeartRequest {
    @SerializedName("chlId") private Long chlId;
    @SerializedName("time") private LocalDateTime time;
    @SerializedName("bpm") private int bpm;
    @SerializedName("chk") private LocalDateTime chk;

    public HeartRequest(long chlId, LocalDateTime time, int bpm, LocalDateTime chk){
        this.chlId = chlId;
        this.time = time;
        this.bpm = bpm;
        this.chk = chk;
    }
}