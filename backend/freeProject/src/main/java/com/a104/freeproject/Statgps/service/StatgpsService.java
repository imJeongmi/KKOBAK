package com.a104.freeproject.Statgps.service;

import com.a104.freeproject.Statgps.request.GpsInputRequest;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface StatgpsService {

    boolean addData(GpsInputRequest input, HttpServletRequest req) throws NotFoundException;

}
