package com.example.kkobak.data.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class JudgeReq {
    @SerializedName("cid") private Long chlId;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("startTime") private LocalDateTime time;

    public JudgeReq() {}

    public JudgeReq(Long chlId, String lat, String lng, LocalDateTime time) {
        this.chlId = chlId;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }

    public Long getChlId() {
        return chlId;
    }

    public void setChlId(Long chlId) {
        this.chlId = chlId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
