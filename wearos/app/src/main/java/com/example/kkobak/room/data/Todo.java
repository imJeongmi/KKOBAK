package com.example.kkobak.room.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long chlId;
    private long categoryId;
    private long detailCategoryId;
    private String contents;
    private Boolean done;
    private String title;
    private int goal;
    private String unit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChlId() {
        return chlId;
    }

    public void setChlId(long chlId) {
        this.chlId = chlId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getDetailCategoryId() {
        return detailCategoryId;
    }

    public void setDetailCategoryId(long detailCategoryId) {
        this.detailCategoryId = detailCategoryId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    public Todo(long chlId, long categoryId, long detailCategoryId, String contents, Boolean done, String title, int goal, String unit) {
        this.chlId = chlId;
        this.categoryId = categoryId;
        this.detailCategoryId = detailCategoryId;
        this.contents = contents;
        this.done = done;
        this.title = title;
        this.goal = goal;
        this.unit = unit;
    }
}
