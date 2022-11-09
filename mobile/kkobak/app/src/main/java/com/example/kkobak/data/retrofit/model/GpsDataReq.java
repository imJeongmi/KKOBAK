package com.example.kkobak.data.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class GpsDataReq {
    @SerializedName("chlId") private Long chlId;
    @SerializedName("time") private String time;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("chk") private String chk;

    public GpsDataReq(long chlId, String time, String lat, String lng, String chk){
        this.chlId = chlId;
        this.time = time;
        this.lat = lat;
        this.lng = lng;
        this.chk = chk;
    }

    public Long getChlId() {
        return chlId;
    }

    public void setChlId(Long chlId) {
        this.chlId = chlId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getChk() {
        return chk;
    }

    public void setChk(String chk) {
        this.chk = chk;
    }
}
