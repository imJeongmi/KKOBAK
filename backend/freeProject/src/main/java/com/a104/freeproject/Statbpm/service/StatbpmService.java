package com.a104.freeproject.Statbpm.service;

import com.a104.freeproject.Statbpm.request.BpmInputRequest;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface StatbpmService {

    boolean addData(BpmInputRequest input, HttpServletRequest req) throws NotFoundException;

}
