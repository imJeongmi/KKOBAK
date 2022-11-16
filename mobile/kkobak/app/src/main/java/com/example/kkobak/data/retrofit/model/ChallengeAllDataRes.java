package com.example.kkobak.data.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class ChallengeAllDataRes {
    @SerializedName("imgurl")
    private String imgUrl;
    @SerializedName("done")
    private boolean done;
    @SerializedName("chlId")
    private int id;
    @SerializedName("detailCategoryId")
    private int detailCategoryId;
    @SerializedName("goal")
    private int goal;
    @SerializedName("title")
    private String title;
    @SerializedName("endTime")
    private String endTime;
    @SerializedName("contents")
    private String contents;
    @SerializedName("watch")
    private boolean watch;

    public ChallengeAllDataRes() {

    }

    public ChallengeAllDataRes(String imgUrl, boolean done, int id, int detailCategoryId, int goal, String title, String endTime, String contents, boolean watch) {
        this.imgUrl = imgUrl;
        this.done = done;
        this.id = id;
        this.detailCategoryId = detailCategoryId;
        this.goal = goal;
        this.title = title;
        this.endTime = endTime;
        this.contents = contents;
        this.watch = watch;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDetailCategoryId() {
        return detailCategoryId;
    }

    public void setDetailCategoryId(int detailCategoryId) {
        this.detailCategoryId = detailCategoryId;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isWatch() {
        return watch;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }
}
