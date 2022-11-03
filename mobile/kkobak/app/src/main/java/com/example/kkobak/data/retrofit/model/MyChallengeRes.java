package com.example.kkobak.data.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyChallengeRes {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("imgurl")
    @Expose
    private String imgUrl;

    @SerializedName("startTime")
    @Expose
    private String startTime;

    @SerializedName("endTime")
    @Expose
    private String endTime;

    @SerializedName("roomtype")
    @Expose
    private int roomType;

    @SerializedName("chlId")
    @Expose
    private int id;

    @SerializedName("watch")
    @Expose
    private boolean watch;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isWatch() {
        return watch;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }

    public MyChallengeRes(String title, String imgUrl, String startTime, String endTime, int roomType, int id, boolean watch) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomType = roomType;
        this.id = id;
        this.watch = watch;
    }

    public MyChallengeRes() {
    }
}
