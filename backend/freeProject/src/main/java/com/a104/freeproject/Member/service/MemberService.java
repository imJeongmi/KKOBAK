package com.a104.freeproject.Member.service;

import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.request.*;
import com.a104.freeproject.Member.response.EmailResponse;
import com.a104.freeproject.Member.response.MyInfoResponse;
import com.a104.freeproject.Member.response.TokenResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface MemberService {
    TokenResponse join(JoinRequest input) throws NotFoundException;
    TokenResponse login(LoginRequest input);
    boolean emailCheck(String email);
    boolean pwCheck(String pw);
    boolean nameCheck(String name);
    Member findEmailbyToken(HttpServletRequest req) throws NotFoundException;
    boolean regWatch(WatchRequest input, HttpServletRequest req) throws NotFoundException;
    boolean changeNick(NickRequest input, HttpServletRequest req) throws NotFoundException;
    boolean changePhone(PhoneRequest input, HttpServletRequest req) throws NotFoundException;
    boolean delWatch(WatchRequest input, HttpServletRequest req) throws NotFoundException;
    MyInfoResponse getMyInfo(HttpServletRequest req) throws NotFoundException;
    EmailResponse getUserInfo(NickRequest input);

}
