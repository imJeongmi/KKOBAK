package com.a104.freeproject.Challenge.service;

import com.a104.freeproject.Challenge.request.registerRequest;
import com.a104.freeproject.HashTag.entity.ChlTag;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ChallengeService {
    boolean register(registerRequest input, HttpServletRequest req) throws NotFoundException;
    List<ChlTag> test(Long cNum);
}
