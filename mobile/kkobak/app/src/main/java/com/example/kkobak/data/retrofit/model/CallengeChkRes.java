package com.example.kkobak.data.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class CallengeChkRes {
    @SerializedName("goal") private int goal;
    @SerializedName("cnt") private int cnt;
    @SerializedName("done") private boolean done;

    public CallengeChkRes() {
    }

    public CallengeChkRes(int goal, int cnt, boolean done) {
        this.goal = goal;
        this.cnt = cnt;
        this.done = done;
    }

    @Override
    public String toString() {
        return "CallengeChkRes{" +
                "goal=" + goal +
                ", cnt=" + cnt +
                ", done=" + done +
                '}';
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
