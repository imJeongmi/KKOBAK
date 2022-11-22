package com.a104.kkobak.data.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class MemberInfoRes {
    @SerializedName("nickName")
    private String nickName;
    @SerializedName("email")
    private String email;
    @SerializedName("imgurl")
    private String imgUrl;

    public MemberInfoRes() {

    }

    public MemberInfoRes(String nickName, String email, String imgUrl) {
        this.nickName = nickName;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
