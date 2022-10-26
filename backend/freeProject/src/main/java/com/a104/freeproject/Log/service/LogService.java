package com.a104.freeproject.Log.service;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Log.request.ChangeStatusRequest;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public interface LogService {
    boolean createLog(PrtChl prtChl) throws NotFoundException;
    void updateDayLog();
    boolean changeTodoList(ChangeStatusRequest input, HttpServletRequest req) throws NotFoundException;
}
