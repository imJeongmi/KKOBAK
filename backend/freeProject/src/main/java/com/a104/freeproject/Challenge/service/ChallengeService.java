package com.a104.freeproject.Challenge.service;

import com.a104.freeproject.Challenge.request.JudgeRequest;
import com.a104.freeproject.Challenge.request.registerRequest;
import com.a104.freeproject.Challenge.response.*;
import com.a104.freeproject.HashTag.entity.ChlTag;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ChallengeService {
    Long register(registerRequest input, HttpServletRequest req) throws NotFoundException;

    List<ChlUserNameResponse> getUserInfo(Long chlId) throws NotFoundException;

    List<ChlUserSimpleStatResponse> getUserSimpleStatInfo(Long chlId) throws NotFoundException;

    List<ChallengeListResponse> getChallengePageList(int page) throws NotFoundException;

    List<ChallengeListResponse> getChallengePageListByCategory(int page, Long id) throws NotFoundException;

    List<ChallengeListResponse> getChallengePageListByDetailCategory(int page, Long id) throws NotFoundException;

    List<ChallengeListResponse> getChallengePageListByTitle(int page, String word) throws NotFoundException;

    List<ChallengeListResponse> getChallengePageListByNickName(int page, String nickName) throws NotFoundException;
    List<ChallengeListResponse> getChallengePageListByTag(int page, String tag) throws NotFoundException;
    ChallengeListResponse getChallenge(Long id) throws NotFoundException;
    boolean checkPassword(Long id, String password) throws NotFoundException;
    int getChallengePageCnt(Pageable pageable);
    List<String> findDoneDate(long chlId, int year, int month, HttpServletRequest req) throws NotFoundException;
    List<useWatchResponse> findWatchUse(boolean useWatch, Pageable pageable, HttpServletRequest req) throws NotFoundException;
    int findWatchCnt(boolean useWatch, Pageable pageable, HttpServletRequest req) throws NotFoundException;
    boolean participateChl(Long chlId, int alarmType, HttpServletRequest req) throws NotFoundException;
    boolean findChlDone(Long chlId, HttpServletRequest req) throws NotFoundException;
    boolean judgeDone(JudgeRequest input, HttpServletRequest req) throws NotFoundException;
    CntResponse chkCid(Long cid, HttpServletRequest req) throws NotFoundException;
    void changeCnt (Long cid, int cnt, HttpServletRequest req) throws NotFoundException;
    List<ChlSimpleResponse> getGroupList(Pageable pageable) throws NotFoundException;
    int getGroupListCnt(Pageable pageable) throws NotFoundException;
    boolean changeStateChl(Long cid, int type, HttpServletRequest req) throws NotFoundException;
    boolean findIsParticipate(Long cid, HttpServletRequest req) throws NotFoundException;
    List<ChlDoneResponse> getLogs(Long cid, HttpServletRequest req) throws NotFoundException;
    List<RunTotalStatResponse> getRunStatList(Long cid, HttpServletRequest req) throws NotFoundException;
    List<MedTotalStatResponse> getMedStatList(Long cid, HttpServletRequest req) throws NotFoundException;
    List<ChlRankResponse> getRank(Long cid) throws NotFoundException;
    List<HabitResponse> getHabitCntList(Long cid, HttpServletRequest req) throws NotFoundException;
}
