package com.example.kkobak.repository.response;

import com.google.gson.annotations.SerializedName;

public class TodoListResponse {
    @SerializedName("chlId") private int chlId;
    @SerializedName("done") private int done;
    @SerializedName("title") private int title;

    public int getChlId() {
        return chlId;
    }

    public void setChlId(int chlId) {
        this.chlId = chlId;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }
}
