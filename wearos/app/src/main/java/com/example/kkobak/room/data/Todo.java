package com.example.kkobak.room.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int chlId;
    private Boolean done;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChlId() {
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

    public Todo(int chlId, Boolean done, String title) {
        this.chlId = chlId;
        this.done = done;
        this.title = title;
    }
}
