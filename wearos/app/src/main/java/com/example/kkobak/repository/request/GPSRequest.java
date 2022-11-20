package com.example.kkobak.repository.request;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GPSRequest {
    @SerializedName("chlId") private Long chlId;
    @SerializedName("time") private String time;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("chk") private String chk;

    public GPSRequest(long chlId, String time, String lat, String lng, String chk){
        this.chlId = chlId;
        this.time = time;
        this.lat = lat;
        this.lng = lng;
        this.chk = chk;
    }
}
