package com.a104.freeproject.Alarm.service;

import com.a104.freeproject.Alarm.entitiy.Fcm;
import com.a104.freeproject.Alarm.repository.FcmRepository;
import com.a104.freeproject.Alarm.request.FcmMessage;
import com.a104.freeproject.Alarm.request.FcmRequest;
import com.a104.freeproject.Alarm.request.Notification;
import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.repository.ChallengeRepository;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.PrtChl.repository.PrtChlRepository;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService {

    private final String fbURL = "https://fcm.googleapis.com/fcm/send";
    private final String watchServerKey = "AAAA0iY1G-A:APA91bHdeR82-1eZRG16BnFByHzU9UL5jZ0GpSqjvyE7jKGPabhLSLKp-Hm7BXlda9A_E4Ws4TG1R3YoUe062C9rseckNSKDCqoxyZZSiTB388R8JqGft7kC4_cjj-pD-aOWnY93sQeh";
    private final String watchFcmToken = "etFSbhqxSjOoNz4D7v7EE_:APA91bF9g_QuPhZsoVeM62JhWALayM2oTxDklKKI5ECBdLrvtotWn0VlnuxEL016qltHwRaJqFbRqd1TavtPZu1a_IyrHmO93XP_THkSF2wX5WHwqunB3vlM0l0LDJI5TEiGb9HwInWU";

    private final MemberServiceImpl memberService;

    private final FcmRepository fcmRepository;

    private final ChallengeRepository challengeRepository;
    private final PrtChlRepository prtChlRepository;


    public boolean regWatch(FcmRequest fcm, HttpServletRequest req) throws NotFoundException{
        Member member = memberService.findEmailbyToken(req);
        Long memberId =member.getId();
        if(fcmRepository.existsByMemberId(memberId)){
            Fcm f = fcmRepository.findByMemberId(memberId);
            f.setWatchToken(fcm.getToken());
        }
        else{
            Fcm f = Fcm.builder().member(member).watchToken(fcm.getToken()).build();
            fcmRepository.save(f);
        }
        return true;
    }

    public boolean regPhone(FcmRequest fcm, HttpServletRequest req) throws NotFoundException{
        Member member = memberService.findEmailbyToken(req);
        Long memberId =member.getId();
        if(fcmRepository.existsByMemberId(memberId)){
            Fcm f = fcmRepository.findByMemberId(memberId);
            f.setPhoneToken(fcm.getToken());
        }
        else{
            Fcm f = Fcm.builder().member(member).phoneToken(fcm.getToken()).build();
            fcmRepository.save(f);
        }
        return true;
    }

    public boolean sendMessage(String myFcmToken){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        //서버키 세팅
        header.set("Authorization","key="+watchServerKey);
        // 알림 내용 설정
        Notification notification = Notification.builder().title("알림 보낼게요").body("이런내용의 알림입니다.").message("메세지").build();

        if(myFcmToken == null){
            myFcmToken=watchFcmToken;
        }

        // 메세지 세팅
        FcmMessage message = FcmMessage.builder().to(myFcmToken)
                .priority("high")
                .notification(notification)
                .build();

        HttpEntity<?> entity = new HttpEntity<>(message, header);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(fbURL).build();
        restTemplate.exchange(uri.toString(), HttpMethod.POST, entity, String.class);
        System.out.println("보냈습니다");


        return true;
    }

    // 매분마다 알림 보내기
//    @Scheduled(cron = "0 * * * * *")
    public void sendAlarm(){
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        String alarmTime = hour+":"+minute;
        System.out.println(alarmTime);
        List<Challenge> chlList = challengeRepository.findAllByAlarmAndIsFin(alarmTime,false);

        for (Challenge challenge : chlList) {
            Long chlId = challenge.getId();
            List<PrtChl> prtChlList = prtChlRepository.findAllByChallenge(challenge);
            for (PrtChl prtChl : prtChlList) {
                Long memberId = prtChl.getMember().getId();
                Fcm fcm = fcmRepository.findByMemberId(memberId);
                //추가해야할거 엄청많음
                sendMessage(fcm.getPhoneToken());
                sendMessage(fcm.getWatchToken());
            }
        }


    }

}
