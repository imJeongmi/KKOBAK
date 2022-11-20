package com.a104.kkobak.data.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyChallengeDetailRes {
    @SerializedName("categoryId")
    @Expose
    private int categoryId;

    @SerializedName("detailCategoryId")
    @Expose
    private int detailCategoryId;

    @SerializedName("writer")
    @Expose
    private int writer;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("contents")
    @Expose
    private String contents;

    @SerializedName("imgurl")
    @Expose
    private String imgUrl;

    @SerializedName("watch")
    @Expose
    private boolean watch;

    @SerializedName("roomtype")
    @Expose
    private int roomType;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("limitPeolple")
    @Expose
    private int limitPeople;

    @SerializedName("currentNum")
    @Expose
    private int currentNum;

    @SerializedName("alarm")
    @Expose
    private String alarm;

    @SerializedName("goal")
    @Expose
    private int goal;

    @SerializedName("unit")
    @Expose
    private String unit;

    @SerializedName("nickName")
    @Expose
    private String nickName;

    @SerializedName("startTime")
    @Expose
    private String startTime;

    @SerializedName("endTime")
    @Expose
    private String endTime;

    @SerializedName("tagList")
    @Expose
    private List<String> tagList;

    @SerializedName("fin")
    @Expose
    private boolean fin;

    public MyChallengeDetailRes(int categoryId, int detailCategoryId, int writer, String title, String contents, String imgUrl, boolean watch, int roomType, String password, int limitPeople, int currentNum, String alarm, int goal, String unit, String nickName, String startTime, String endTime, List<String> tagList, boolean fin) {
        this.categoryId = categoryId;
        this.detailCategoryId = detailCategoryId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.imgUrl = imgUrl;
        this.watch = watch;
        this.roomType = roomType;
        this.password = password;
        this.limitPeople = limitPeople;
        this.currentNum = currentNum;
        this.alarm = alarm;
        this.goal = goal;
        this.unit = unit;
        this.nickName = nickName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tagList = tagList;
        this.fin = fin;
    }

    public MyChallengeDetailRes() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getDetailCategoryId() {
        return detailCategoryId;
    }

    public void setDetailCategoryId(int detailCategoryId) {
        this.detailCategoryId = detailCategoryId;
    }

    public int getWriter() {
        return writer;
    }

    public void setWriter(int writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "MyChallengeDetailRes{" +
                "categoryId=" + categoryId +
                ", detailCategoryId=" + detailCategoryId +
                ", writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", watch=" + watch +
                ", roomType=" + roomType +
                ", password='" + password + '\'' +
                ", limitPeople=" + limitPeople +
                ", currentNum=" + currentNum +
                ", alarm='" + alarm + '\'' +
                ", goal=" + goal +
                ", unit='" + unit + '\'' +
                ", nickName='" + nickName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", tagList=" + tagList +
                ", fin=" + fin +
                '}';
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isWatch() {
        return watch;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLimitPeople() {
        return limitPeople;
    }

    public void setLimitPeople(int limitPeople) {
        this.limitPeople = limitPeople;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }
}
