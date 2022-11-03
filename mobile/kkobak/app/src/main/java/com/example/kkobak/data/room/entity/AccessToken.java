package com.example.kkobak.data.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AccessToken")
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

    public AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

//    public AccessToken(int id, String accessToken) {
 //       this.id = id;
  //      this.accessToken = accessToken;
//    }
}
