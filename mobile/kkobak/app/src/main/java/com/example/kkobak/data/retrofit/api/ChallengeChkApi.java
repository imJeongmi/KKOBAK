package com.example.kkobak.data.retrofit.api;

import com.example.kkobak.data.retrofit.service.ChallengeService;

public class ChallengeChkApi extends BaseApi {

    public static ChallengeService getService() {
        return (getInstance().create(ChallengeService.class));
    }
}
