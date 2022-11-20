package com.a104.kkobak.data.retrofit.api;

import com.a104.kkobak.data.retrofit.service.GpsDataService;

public class GpsDataApi extends BaseApi {

    public static GpsDataService sendGpsData() {
        return (getInstance().create(GpsDataService.class));
    }
}
