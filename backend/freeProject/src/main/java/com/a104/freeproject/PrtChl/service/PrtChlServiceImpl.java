package com.a104.freeproject.PrtChl.service;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.entity.ChlTime;
import com.a104.freeproject.Challenge.repository.ChallengeRepository;
import com.a104.freeproject.Challenge.repository.ChlTimeRepository;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.PrtChl.repository.PrtChlRepository;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class PrtChlServiceImpl implements PrtChlService{

    private final ChallengeRepository challengeRepository;
    private final PrtChlRepository prtChlRepository;
    private final MemberServiceImpl memberService;
    private final ChlTimeRepository chlTimeRepository;

    @Override
    public boolean participate(Long cid, HttpServletRequest req) throws NotFoundException {
        if(!challengeRepository.existsById(cid)) throw new NotFoundException("존재하지 않는 챌린지입니다.");
        Challenge c = challengeRepository.findById(cid).get();

        Member member;
        try{
            member = memberService.findEmailbyToken(req);
        } catch (Exception e){
            throw e;
        }

        Timestamp time = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        Timestamp now = Timestamp.valueOf(sdf.format(time));

        if(!chlTimeRepository.existsByChallenge(c)) throw new NotFoundException("챌린지 스케쥴러에 존재하지 않습니다. 이 문제가 발생하면 백엔드에 문의하세용");
        ChlTime chl = chlTimeRepository.findByChallenge(c);

        prtChlRepository.save(PrtChl.builder().challenge(c).member(member)
                .is_fin(false).startDate(now).endDate(chl.getEndTime()).build());



        return true;
    }
}
