package com.a104.kkobak.data.retrofit.api;

import com.a104.kkobak.data.retrofit.service.ChallengeService;

public class ChallengeChkApi extends BaseApi {

    public static ChallengeService getService() {
        return (getInstance().create(ChallengeService.class));
    }
}
