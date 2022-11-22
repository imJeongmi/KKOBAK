package com.a104.freeproject.Alarm.controller;

import com.a104.freeproject.Alarm.request.FcmRequest;
import com.a104.freeproject.Alarm.service.FcmServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
@ApiOperation(value = "[알림] Alarm Controller", notes = "알림 보내고 싶을때 쓸수있습니다.")
public class FcmController {

    final FcmServiceImpl fcmServiceImpl;

    @PostMapping("/register/watch")
    @ApiOperation(value="와치의 FCM 토큰 등록", notes="와치 로그인 성공시 호출해주세요")
    public ResponseEntity<Boolean> regWatch(@RequestBody FcmRequest fcm,  HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(fcmServiceImpl.regWatch(fcm,req));
    }

    @PostMapping("/register/phone")
    @ApiOperation(value="스마트폰의 FCM 토큰 등록", notes="스마트폰 로그인 성공시 호출해주세요")
    public ResponseEntity<Boolean> regPhone(@RequestBody FcmRequest fcm,  HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(fcmServiceImpl.regPhone(fcm,req));
    }

    @ApiOperation(value="단순 메세지 전송", notes="메세지 전송 테스트용 스웨거 입니다.")
    @GetMapping("/send")
    public ResponseEntity<Boolean> sendMessage(String FcmToken) throws NotFoundException{
        return ResponseEntity.ok().body(fcmServiceImpl.sendMessage(FcmToken));
    }
}
