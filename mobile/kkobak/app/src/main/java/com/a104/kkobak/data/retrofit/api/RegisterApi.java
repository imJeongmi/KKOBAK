package com.a104.kkobak.data.retrofit.api;

import com.a104.kkobak.data.retrofit.service.RegisterService;

public class RegisterApi extends BaseApi {

    public static RegisterService doRegistService() {
        return (getInstance().create(RegisterService.class));
    }

}
