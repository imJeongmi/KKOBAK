package com.example.kkobak.room.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AccessToken {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String accessToken;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
