package com.a104.freeproject.Member.service;

import com.a104.freeproject.Challenge.response.ChlSimpleResponse;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.request.*;
import com.a104.freeproject.Member.response.*;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MemberService {
    TokenResponse join(JoinRequest input) throws NotFoundException;
    TokenResponse login(LoginRequest input);
    boolean emailCheck(String email) throws NotFoundException;
    boolean pwCheck(String pw);
    boolean nameCheck(String name);
    Member findEmailbyToken(HttpServletRequest req) throws NotFoundException;
    boolean regWatch(WatchRequest input, HttpServletRequest req) throws NotFoundException;
    boolean changeNick(NickRequest input, HttpServletRequest req) throws NotFoundException;
    boolean changePhone(PhoneRequest input, HttpServletRequest req) throws NotFoundException;
    boolean delWatch(WatchRequest input, HttpServletRequest req) throws NotFoundException;
    MyInfoResponse getMyInfo(HttpServletRequest req) throws NotFoundException;
    EmailResponse getUserInfo(NickRequest input);
    List<ChlSimpleResponse> getUserChallenge(Pageable pageable, HttpServletRequest req) throws NotFoundException;
    int getMyChlTotalSum(Pageable pageable, HttpServletRequest req) throws NotFoundException;
    List<ChlSimpleResponse> getMemberChallenge(Pageable pageable, NickRequest input) throws NotFoundException;
    int getUserChlTotalSum(Pageable pageable, NickRequest input) throws NotFoundException;
    ChlSimpleStatResponse getChlSimpleStatistics(HttpServletRequest req) throws NotFoundException;
    List<MonthChlResponse> monthChlInfo(Long chlId, String year, String month, HttpServletRequest req) throws NotFoundException;
    List<TodoListInfoResponse> getTodoListInfo(DayRequest day, HttpServletRequest req) throws NotFoundException;
}
