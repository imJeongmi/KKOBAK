package com.a104.freeproject.Challenge.controller;

import com.a104.freeproject.Challenge.request.registerRequest;
import com.a104.freeproject.Challenge.service.ChallengeServiceImpl;
import com.a104.freeproject.HashTag.entity.ChlTag;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
@ApiOperation(value="[챌린지] Challenge Controller")
public class ChallengeController {

    private final ChallengeServiceImpl challengeService;

    @PostMapping("/register")
    @ApiOperation(value = "[수정필요] 챌린지 등록", notes = "return 값 변경 원하시면 MM 주세용\n"
            +"startTime, endTime 걍 스웨거 무시하시고 \"startTime\": \"2022-10-22T22:37\" 형식으로 입력해주시면 됩니당\n"
            +"현재 해시태그, 카테고리, 스케쥴러와는 연결 완료. 유저와 연결 필요 + 알람???")
    public ResponseEntity<Boolean> register(@RequestBody registerRequest input, HttpServletRequest req) throws NotFoundException{
        return ResponseEntity.ok().body(challengeService.register(input, req));
    }


}
