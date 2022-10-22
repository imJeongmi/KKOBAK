package com.a104.freeproject.PrtChl.service;

import com.a104.freeproject.advice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface PrtChlService {

    boolean participate(Long cid, HttpServletRequest req) throws NotFoundException;

}
