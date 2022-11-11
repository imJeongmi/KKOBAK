package com.example.kkobak.data.retrofit.api;

import com.example.kkobak.data.retrofit.service.BpmDataService;

public class BpmDataApi extends BaseApi {
    public static BpmDataService getBpmService() {
        return (getInstance().create(BpmDataService.class));
    }
}
