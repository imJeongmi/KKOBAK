package com.a104.freeproject.Statbpm.service;

import com.a104.freeproject.Statbpm.request.BpmInputRequest;
import com.a104.freeproject.Statbpm.response.BpmResultResponse;
import com.a104.freeproject.Statgps.response.ResultResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface StatbpmService {
    boolean addData(BpmInputRequest input, HttpServletRequest req) throws NotFoundException;
    BpmResultResponse getTryList(String year, String month, String day, Long cid, HttpServletRequest req) throws NotFoundException;

}
