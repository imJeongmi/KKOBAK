package com.example.kkobak.ui.notifications;

public class MyChallengeCard {
    private String imgUrl;

    private String startTime;

    private String endTime;

    private String title;

    private int id;

    private boolean watch;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public MyChallengeCard(String imgUrl, String startTime, String endTime, String title, int id, boolean watch) {
        this.imgUrl = imgUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.id = id;
        this.watch = watch;
    }
    public MyChallengeCard(String startTime, String endTime, String title, int id, boolean watch) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.id = id;
        this.watch = watch;
    }

    public MyChallengeCard() {

    }
}
