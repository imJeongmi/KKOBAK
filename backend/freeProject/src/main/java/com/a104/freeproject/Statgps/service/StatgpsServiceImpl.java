package com.a104.freeproject.Statgps.service;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.repository.ChallengeRepository;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.PrtChl.repository.PrtChlRepository;
import com.a104.freeproject.Statgps.entity.Statgps;
import com.a104.freeproject.Statgps.repository.StatgpsRepository;
import com.a104.freeproject.Statgps.request.GpsInputRequest;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.ZoneId;

@Service
@Transactional
@RequiredArgsConstructor
public class StatgpsServiceImpl implements StatgpsService{

    private final MemberServiceImpl memberService;
    private final ChallengeRepository challengeRepository;
    private final PrtChlRepository prtChlRepository;
    private final StatgpsRepository statgpsRepository;

    @Override
    public boolean addData(GpsInputRequest input, HttpServletRequest req) throws NotFoundException {

        Member member = memberService.findEmailbyToken(req);
        if(!challengeRepository.existsById(input.getChlId()))
            throw new NotFoundException("해당 챌린지가 존재하지 않습니다.");
        Challenge c = challengeRepository.findById(input.getChlId()).get();

        if(!prtChlRepository.existsByChallengeAndMember(c,member))
            throw new NotFoundException("참여하지 않은 챌린지입니다.");
        PrtChl p = prtChlRepository.findByChallengeAndMember(c,member);

        Statgps statgps = Statgps.builder().prtChl(p)
                .time(input.getTime().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
                .lat(input.getLat())
                .lng(input.getLng())
                .success(false)
                .chk(input.getChk().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
                .build();

        statgpsRepository.save(statgps);

        return true;
    }
}
