package com.a104.freeproject.Statgps.service;

import com.a104.freeproject.Statgps.request.GpsInputRequest;
import com.a104.freeproject.Statgps.response.ResultResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StatgpsService {

    boolean addData(GpsInputRequest input, HttpServletRequest req) throws NotFoundException;

    ResultResponse getTryList(String year, String month, String day, Long cid, HttpServletRequest req) throws NotFoundException;


}
