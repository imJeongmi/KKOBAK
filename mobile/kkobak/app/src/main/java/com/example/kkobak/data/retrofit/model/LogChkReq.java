package com.example.kkobak.data.retrofit.model;

import android.os.Build;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LogChkReq {
    @SerializedName("chlId")
    private Long chlId;

    @SerializedName("day")
    private String date;

    public LogChkReq(Long chlId, String date) {
        this.chlId = chlId;
        this.date = date;
    }

    public LogChkReq() {
    }

    public Long getChlId() {
        return chlId;
    }

    public void setChlId(Long chlId) {
        this.chlId = chlId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static String makeDate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            return (LocalDate.now(ZoneId.of("Asia/Seoul"))).toString();
        else
           return "Fail makeDate";
    }
}
