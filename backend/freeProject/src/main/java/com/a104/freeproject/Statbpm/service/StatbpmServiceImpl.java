package com.a104.freeproject.Statbpm.service;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.repository.ChallengeRepository;
import com.a104.freeproject.Challenge.service.ChallengeServiceImpl;
import com.a104.freeproject.Log.entity.Log;
import com.a104.freeproject.Log.repository.LogRepository;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.PrtChl.repository.PrtChlRepository;
import com.a104.freeproject.Statbpm.entity.Statbpm;
import com.a104.freeproject.Statbpm.repository.StatbpmRepository;
import com.a104.freeproject.Statbpm.request.BpmInputRequest;
import com.a104.freeproject.Statbpm.response.BpmListResponse;
import com.a104.freeproject.Statbpm.response.BpmResultResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StatbpmServiceImpl implements StatbpmService{

    private final MemberServiceImpl memberService;
    private final ChallengeRepository challengeRepository;
    private final PrtChlRepository prtChlRepository;
    private final StatbpmRepository statbpmRepository;
    private final LogRepository logRepository;

    @Override
    public boolean addData(BpmInputRequest input, HttpServletRequest req) throws NotFoundException {

        Member member = memberService.findEmailbyToken(req);
        if(!challengeRepository.existsById(input.getChlId()))
            throw new NotFoundException("해당 챌린지가 존재하지 않습니다.");
        Challenge c = challengeRepository.findById(input.getChlId()).get();

        if(!prtChlRepository.existsByChallengeAndMember(c,member))
            throw new NotFoundException("참여하지 않은 챌린지입니다.");
        PrtChl p = prtChlRepository.findByChallengeAndMember(c,member);

        Statbpm statbpm = Statbpm.builder().prtChl(p)
                .time(input.getTime().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
                .bpm(input.getBpm())
                .success(false)
                .chk(input.getChk().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
                .build();

        statbpmRepository.save(statbpm);

        return true;
    }

    @Override
    public BpmResultResponse getTryList(String year, String month, String day, Long cid, HttpServletRequest req) throws NotFoundException {
        Member member = memberService.findEmailbyToken(req);
        System.out.println("member 찾음");
        if(!challengeRepository.existsById(cid))
            throw new NotFoundException("해당 챌린지가 존재하지 않습니다.");
        Challenge c = challengeRepository.findById(cid).get();
        if(c.isFin()) throw new NotFoundException("이미 종료한 챌린지입니다.");
        System.out.println("챌린지 찾음");

        if(!prtChlRepository.existsByChallengeAndMember(c,member))
            throw new NotFoundException("참여하지 않은 챌린지입니다.");
        PrtChl p = prtChlRepository.findByChallengeAndMember(c,member);
        if(p.is_fin()) throw new NotFoundException("이미 끝낸 챌린지 입니다.");
        System.out.println("p 찾음");
        LocalDate date = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
        System.out.println("date = " + date);

        if(!logRepository.existsByPrtChlAndDate(p,date))
            return BpmResultResponse.builder()
                .flag(false)
                .bpmList(new LinkedList<BpmListResponse>())
                .maxBpm(0).minBpm(0).avgBpm(0)
                .build();
        System.out.println("로그 존재함.");
        Log log = logRepository.findByPrtChlAndDate(p,date);
        List<Statbpm> statbpmList = statbpmRepository.findByChkAndPrtChl(date.toString(),p);
        if(statbpmList.size()==0)
            return BpmResultResponse.builder()
                    .flag(false)
                    .bpmList(new LinkedList<BpmListResponse>())
                    .maxBpm(0).minBpm(0).avgBpm(0)
                    .build();

        boolean flag = statbpmList.get(statbpmList.size()-1).isSuccess();
        LocalDateTime sendTime = statbpmList.get(statbpmList.size()-1).getChk();
        if(!flag){
            for(int i = statbpmList.size()-2;i>=0;i--){
                if(flag) {
                    flag = statbpmList.get(statbpmList.size()-1).isSuccess();
                    sendTime = statbpmList.get(statbpmList.size()-1).getChk();
                    break;
                }
            }
        }

        List<Statbpm> list = statbpmRepository.findByChkTimeAndPrtChl(p,sendTime);
        List<BpmListResponse> output = new LinkedList<>();
        int maxbpm=list.get(0).getBpm();
        int minbpm=list.get(0).getBpm();
        int sumbpm=0;
        for(Statbpm statbpm:list){
            sumbpm+=statbpm.getBpm();
            maxbpm=Math.max(maxbpm,statbpm.getBpm());
            minbpm=Math.min(minbpm,statbpm.getBpm());

            output.add(BpmListResponse.builder()
                    .bpm(statbpm.getBpm()).time(statbpm.getTime())
                    .build());
        }

        int avgbpm=sumbpm/list.size();

        return BpmResultResponse.builder()
                .flag(flag)
                .bpmList(output)
                .maxBpm(maxbpm)
                .minBpm(minbpm)
                .avgBpm(avgbpm)
                .build();
    }
}
