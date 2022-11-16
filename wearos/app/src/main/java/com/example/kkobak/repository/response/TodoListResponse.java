package com.example.kkobak.repository.response;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class TodoListResponse {
    @SerializedName("chlId") private Long chlId;
    @SerializedName("done") private boolean done;
    @SerializedName("title") private String title;
    @SerializedName("contents") private String contents;
    @SerializedName("categoryId") private Long categoryId;
    @SerializedName("detailCategoryId") private Long detailCategoryId;
    @SerializedName("goal") private int goal;
    @SerializedName("unit") private String unit;
    @SerializedName("cnt") private int cnt;
    @SerializedName("kkobak") private int kkobak;

    public Long getChlId() {
        return chlId;
    }

    public void setChlId(Long chlId) {
        this.chlId = chlId;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getDetailCategoryId() {
        return detailCategoryId;
    }

    public void setDetailCategoryId(Long detailCategoryId) {
        this.detailCategoryId = detailCategoryId;
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

    public int getKkobak() {
        return kkobak;
    }

    public void setKkobak(int kkobak) {
        this.kkobak = kkobak;
    }

    public int getCnt() {
        return this.cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

}
