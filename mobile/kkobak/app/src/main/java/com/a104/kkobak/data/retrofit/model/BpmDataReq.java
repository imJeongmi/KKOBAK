package com.a104.kkobak.data.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class BpmDataReq {
    @SerializedName("bpm")
    private int bpm;

    @SerializedName("chk")
    private String chk;

    @SerializedName("chlId")
    private Long chlId;

    @SerializedName("time")
    private String time;

    public BpmDataReq() {
    }

    public BpmDataReq(int bpm, String chk, Long chlId, String time) {
        this.bpm = bpm;
        this.chk = chk;
        this.chlId = chlId;
        this.time = time;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public String getChk() {
        return chk;
    }

    public void setChk(String chk) {
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
}
