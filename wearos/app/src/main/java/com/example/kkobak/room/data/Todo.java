package com.example.kkobak.room.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long chlId;
    private Boolean done;
    private String title;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getChlId() {
        return chlId;
    }

    public void setChlId(int chlId) {
        this.chlId = chlId;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Todo(long chlId, Boolean done, String title) {
        this.chlId = chlId;
        this.done = done;
        this.title = title;
    }
}
