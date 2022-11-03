package com.example.kkobak.repository.response;

import com.google.gson.annotations.SerializedName;

public class TodoListResponse {
    @SerializedName("chlId") private int chlId;
    @SerializedName("done") private boolean done;
    @SerializedName("title") private String title;

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
