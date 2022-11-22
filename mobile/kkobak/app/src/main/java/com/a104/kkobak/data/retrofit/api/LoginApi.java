package com.a104.kkobak.data.retrofit.api;

import com.a104.kkobak.data.retrofit.service.LoginService;

public class LoginApi extends BaseApi {
    public static LoginService doLoginService() {
        return (getInstance().create(LoginService.class));
    }
}
