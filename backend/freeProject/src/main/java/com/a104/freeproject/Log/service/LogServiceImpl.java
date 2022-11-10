package com.a104.freeproject.Log.service;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.repository.ChallengeRepository;
import com.a104.freeproject.Challenge.request.JudgeRequest;
import com.a104.freeproject.Challenge.service.ChallengeServiceImpl;
import com.a104.freeproject.Log.entity.Log;
import com.a104.freeproject.Log.repository.LogRepository;
import com.a104.freeproject.Log.request.ChangeStatusRequest;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.repository.MemberRepository;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.PrtChl.repository.PrtChlRepository;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final PrtChlRepository prtChlRepository;
    private final LogRepository logRepository;
    private final ChallengeRepository challengeRepository;
    private final MemberServiceImpl memberService;
    private final ChallengeServiceImpl challengeService;

    @Override
    public boolean createLog(PrtChl prtChl) throws NotFoundException {

        if(!prtChlRepository.existsById(prtChl.getId()))
            throw new NotFoundException("해당 유저는 해당 챌린지에 참여하지 않습니다.");

        logRepository.save(Log.builder()
                .prtChl(prtChl)
                .date(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .build());

        return true;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?") // 매일 0시 0분에 실행
    public void updateDayLog() {
        List<PrtChl> prtList = prtChlRepository.findAll();
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        for(PrtChl p : prtList){

            if(logRepository.existsByPrtChlAndDate(p,today)) continue;
            if(p.is_fin()) continue; // 이미 끝난 챌린지, 내가 그만 둔 챌린지라면 더 추가하지 않는다.

            logRepository.save(Log.builder()
                    .prtChl(p).date(today).build());

            p.setFailDay(p.getFailDay()+1);
            prtChlRepository.save(p);
        }
    }

    @Override
    public boolean changeTodoList(ChangeStatusRequest input, HttpServletRequest req) throws NotFoundException {
        Member member;
        try{
            member = memberService.findEmailbyToken(req);
        } catch (Exception e){
            throw e;
        }

        Challenge challenge = challengeRepository.findById(input.getChlId()).get();
        LocalDate day = input.getDay();

        PrtChl p;
        if(!prtChlRepository.existsByChallengeAndMember(challenge,member))
            throw new NotFoundException("해당 챌린지가 존재하지 않습니다.");
        p = prtChlRepository.findByChallengeAndMember(challenge,member);
        if(p.is_fin()) throw new NotFoundException("해당 챌린지는 종료되었습니다. 확인해주세요.");

        Log log;
        if(!logRepository.existsByPrtChlAndDate(p,day))
            throw new NotFoundException("로그 생성이 되어있지 않습니다. 이 에러가 뜨면 백엔드에 문의해주세용.");

        log = logRepository.findByPrtChlAndDate(p,day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

        if(!log.isFin()) {
            log.setCompleteTime(Timestamp.valueOf(LocalDateTime.now()));
            p.setSucDay(p.getSucDay()+1);
            p.setFailDay(p.getFailDay()-1);
        }
        else {
            log.setCompleteTime(null);
            p.setSucDay(p.getSucDay()-1);
            p.setFailDay(p.getFailDay()+1);
        }
        challengeService.judgeDone(JudgeRequest.builder().cid(challenge.getId()).build(),req);
        logRepository.save(log);

        return true;
    }
}
