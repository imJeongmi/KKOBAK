package com.a104.kkobak.data.retrofit.api;

import com.a104.kkobak.data.retrofit.service.MemberInfoService;

public class MemberInfoApi extends BaseApi {
    public static MemberInfoService getService() {
        return (getInstance().create(MemberInfoService.class));
    }
}
