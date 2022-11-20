package com.a104.kkobak.data.retrofit.model;

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
    @SerializedName("unit")
    private String unit;
    @SerializedName("nowCnt")
    private int cnt;
    @SerializedName("watch")
    private boolean watch;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "ChallengeAllDataRes{" +
                "imgUrl='" + imgUrl + '\'' +
                ", done=" + done +
                ", id=" + id +
                ", detailCategoryId=" + detailCategoryId +
                ", goal=" + goal +
                ", title='" + title + '\'' +
                ", endTime='" + endTime + '\'' +
                ", contents='" + contents + '\'' +
                ", unit='" + unit + '\'' +
                ", cnt=" + cnt +
                ", watch=" + watch +
                '}';
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ChallengeAllDataRes() {

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
