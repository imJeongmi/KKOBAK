package com.example.kkobak;

import android.app.Application;

public class KkobakApp extends Application {
    // 전역변수 액세스 토큰(로그인할때 setter로 저장해주세요)
    private String accessToken;
    private String FcmToken;

    // ( (KkobakApp) getApplication() ).getAccessToken(); 처럼 호출
    public String getAccessToken(){
        return accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getFcmToken()  {return FcmToken;}
    public void setFcmToken(String FcmToken){
        this.FcmToken = FcmToken;
    }
}
