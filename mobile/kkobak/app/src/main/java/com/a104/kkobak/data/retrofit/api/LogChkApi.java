package com.a104.kkobak.data.retrofit.api;

import com.a104.kkobak.data.retrofit.service.LogChkService;

public class LogChkApi extends BaseApi {
    public static LogChkService getLogChkService() {
        return (getInstance().create(LogChkService.class));
    }
}
