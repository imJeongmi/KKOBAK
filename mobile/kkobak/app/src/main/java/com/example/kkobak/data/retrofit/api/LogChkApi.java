package com.example.kkobak.data.retrofit.api;

import com.example.kkobak.data.retrofit.service.LogChkService;

public class LogChkApi extends BaseApi {
    public static LogChkService getLogChkService() {
        return (getInstance().create(LogChkService.class));
    }
}
