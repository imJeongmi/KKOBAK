package com.a104.freeproject.Statgps.service;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.repository.ChallengeRepository;
import com.a104.freeproject.Log.entity.Log;
import com.a104.freeproject.Log.repository.LogRepository;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.PrtChl.repository.PrtChlRepository;
import com.a104.freeproject.Statgps.entity.Statgps;
import com.a104.freeproject.Statgps.repository.StatgpsRepository;
import com.a104.freeproject.Statgps.request.GpsInputRequest;
import com.a104.freeproject.Statgps.response.GpsResultResponse;
import com.a104.freeproject.Statgps.response.ResultResponse;
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
public class StatgpsServiceImpl implements StatgpsService{

    private final MemberServiceImpl memberService;
    private final ChallengeRepository challengeRepository;
    private final PrtChlRepository prtChlRepository;
    private final StatgpsRepository statgpsRepository;
    private final LogRepository logRepository;
    private final static int  EARTH_RADIUS = 6371;

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

    @Override
    public ResultResponse getTryList(String year, String month, String day, Long cid, HttpServletRequest req) throws NotFoundException {

        Member member = memberService.findEmailbyToken(req);
        
        if(!challengeRepository.existsById(cid))
            throw new NotFoundException("해당 챌린지가 존재하지 않습니다.");
        Challenge c = challengeRepository.findById(cid).get();

        if(!prtChlRepository.existsByChallengeAndMember(c,member))
            throw new NotFoundException("참여하지 않은 챌린지입니다.");
        PrtChl p = prtChlRepository.findByChallengeAndMember(c,member);

        LocalDate date = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
        
        if(!logRepository.existsByPrtChlAndDate(p,date))
            throw new NotFoundException("오늘 해당 챌린지의 로그가 존재하지 않습니다.");
        Log log = logRepository.findByPrtChlAndDate(p,date);

        List<Statgps> statgpsList = statgpsRepository.findByChkAndPrtChl(date.toString(),p);
        if(statgpsList.size()==0)
            return ResultResponse.builder()
                    .flag(false)
                    .gpsList(new LinkedList<GpsResultResponse>())
                    .total_dist(0)
                    .build();

        boolean flag = statgpsList.get(statgpsList.size()-1).isSuccess();
        LocalDateTime sendTime = statgpsList.get(statgpsList.size()-1).getChk();
        if(!flag){
            for(int i = statgpsList.size()-2;i>=0;i--){
                if(flag) {
                    flag = statgpsList.get(statgpsList.size()-1).isSuccess();
                    sendTime = statgpsList.get(statgpsList.size()-1).getChk();
                    break;
                }
            }
        }

        List<Statgps> list = statgpsRepository.findByChkTimeAndPrtChl(p,sendTime);
        List<GpsResultResponse> output = new LinkedList<>();
        for(Statgps statgps:list){
            output.add(GpsResultResponse.builder()
                    .lat(statgps.getLat()).lng(statgps.getLng()).time(statgps.getTime())
                    .build());
        }

        double dist=0;

        // =============================================================

        // 성영이 오빠가 하면 됨
        // OUTPUT이 LAT, LNG, TIME(LOCALDATETIME) 으로 되어있는 list. 순서대로 나옴 걱정 ㄴㄴ
        // 거리만 바꿔서 dist에 넣어주면 됨. 밑에서 알아서 넣어줌. ex) dist = 123;

        // =============================================================


        return ResultResponse.builder()
                .flag(flag)
                .gpsList(output)
                .total_dist(dist)
                .build();
    }
    
    //거리 계산용 함수
    public static double getDistance(String lat1_s, String lng1_s, String lat2_s, String lng2_s) {

        double lat1 = Double.parseDouble(lat1_s);
        double lng1 = Double.parseDouble(lng1_s);
        double lat2 = Double.parseDouble(lat2_s);
        double lng2 = Double.parseDouble(lng2_s);

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = EARTH_RADIUS * c * 1000;    // 미터로 바꾸기
        return d;
    }

}
