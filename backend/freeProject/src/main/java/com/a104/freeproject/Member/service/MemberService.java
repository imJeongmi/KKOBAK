package com.a104.freeproject.Member.service;

import com.a104.freeproject.Member.request.JoinRequest;
import com.a104.freeproject.Member.request.LoginRequest;
import com.a104.freeproject.Member.response.TokenResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;

public interface MemberService {
    TokenResponse join(JoinRequest input) throws NotFoundException;
    TokenResponse login(LoginRequest input);
    boolean emailCheck(String email);
    boolean pwCheck(String pw);
    boolean nameCheck(String name);
}
