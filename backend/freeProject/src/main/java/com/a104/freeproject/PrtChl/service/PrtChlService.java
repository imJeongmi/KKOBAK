package com.a104.freeproject.PrtChl.service;

import com.a104.freeproject.PrtChl.request.ChlRequest;
import com.a104.freeproject.PrtChl.response.LikeDetail;
import com.a104.freeproject.PrtChl.response.MyChallengeDetailResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PrtChlService {
    boolean participate(Long cid, HttpServletRequest req, int alarm) throws NotFoundException;
    boolean dropChl(Long cid, HttpServletRequest req) throws NotFoundException;

    boolean changeKkobak(ChlRequest chlRequest, HttpServletRequest req) throws NotFoundException;

    MyChallengeDetailResponse getPtrChlDetail(Long cid, HttpServletRequest req) throws NotFoundException;

    List<LikeDetail> getLikeList(HttpServletRequest req) throws NotFoundException;
}
