package com.a104.freeproject.Alarm.service;

import com.a104.freeproject.Alarm.request.FcmRequest;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface FcmService {
    public boolean regWatch(FcmRequest fcm, HttpServletRequest req) throws NotFoundException;

}
