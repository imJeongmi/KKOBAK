package com.example.kkobak.data.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterReq {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("hp")
    @Expose
    private String hp;

    public String getEmail() {
        return email;
    }

    public String getHp() {
        return hp;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("password")
    @Expose
    private String password;
}
