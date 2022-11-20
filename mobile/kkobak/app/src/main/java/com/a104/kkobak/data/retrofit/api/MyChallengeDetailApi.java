package com.a104.kkobak.data.retrofit.api;

import com.a104.kkobak.data.retrofit.service.MyChallengeDetailService;

public class MyChallengeDetailApi extends BaseApi {

    public static MyChallengeDetailService getDetailInfo() {
        return (getInstance().create(MyChallengeDetailService.class));
    }
}
