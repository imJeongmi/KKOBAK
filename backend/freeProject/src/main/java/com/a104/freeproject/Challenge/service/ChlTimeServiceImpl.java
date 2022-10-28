package com.a104.freeproject.Challenge.service;
import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.entity.ChlTime;
import com.a104.freeproject.Challenge.repository.ChallengeRepository;
import com.a104.freeproject.Challenge.repository.ChlTimeRepository;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.PrtChl.repository.PrtChlRepository;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-secret.properties")
})
public class ChlTimeServiceImpl implements ChlTimeService{

    private final ChlTimeRepository chlTimeRepository;
    private final ChallengeRepository challengeRepository;
    private final PrtChlRepository prtChlRepository;

    @Override
    public boolean addRow(Challenge c, Timestamp st, Timestamp ed) throws NotFoundException {
        if(chlTimeRepository.existsByChallenge(c)) throw new NotFoundException("이미 스케쥴러에 등록된 Challenge 입니다.");
        chlTimeRepository.save(ChlTime.builder()
                .challenge(c).startTime(st).endTime(ed).build());
        return true;
    }

    @Override
    @Scheduled(cron = "0 * * * * *") // 1분 주기로 실행
    public void changeState() {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        Timestamp now = Timestamp.valueOf(sdf.format(time));

        List<Challenge> postList = challengeRepository.findAllByStatus(1);
        for(Challenge c : postList){
            ChlTime chlTime = chlTimeRepository.findByChallenge(c);
            if(chlTime.getEndTime().equals(now) || chlTime.getEndTime().before(now)) c.setStatus(2);
            c.setFin(true);
            challengeRepository.save(c);

            List<PrtChl> chlList = c.getChlList();
            for (PrtChl p:chlList){
                p.set_fin(true);
                prtChlRepository.save(p);
            }

        }

        List<Challenge> preList = challengeRepository.findAllByStatus(0);
        for(Challenge c : preList){

            // 현재는 이거를 사용할 일이 거의 없을 것임. 만약에 생기면 챌린지 관련해서 챌린지 create 어쩌고 필요한 부분 넣어야함

            ChlTime chlTime = chlTimeRepository.findByChallenge(c);
            if(chlTime.getStartTime().equals(now) || chlTime.getStartTime().before(now)) c.setStatus(1);
            challengeRepository.save(c);
        }

        System.out.println(now);
    }
}
