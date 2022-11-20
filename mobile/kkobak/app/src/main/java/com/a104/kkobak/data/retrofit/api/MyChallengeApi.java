package com.a104.kkobak.data.retrofit.api;

import com.a104.kkobak.data.retrofit.service.MyChallengeService;

public class MyChallengeApi extends BaseApi {
    public static MyChallengeService getMyChallengeService() {
        return (getInstance().create(MyChallengeService.class));
    }
}
