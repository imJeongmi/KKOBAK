package com.example.kkobak.repository.response;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TodoListResponse {
    @SerializedName("chlId") private int chlId;
    @SerializedName("done") private boolean done;
    @SerializedName("title") private String title;
    @SerializedName("contents") private String contents;
    @SerializedName("categoryId") private Long categoryId;
    @SerializedName("detailCategoryId") private Long detailCategoryId;
    @SerializedName("goal") private int goal;
    @SerializedName("unit") private String unit;
    @SerializedName("kkobak") private int kkobak;

    public int getChlId() {
        return chlId;
    }

    public void setChlId(int chlId) {
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
}
