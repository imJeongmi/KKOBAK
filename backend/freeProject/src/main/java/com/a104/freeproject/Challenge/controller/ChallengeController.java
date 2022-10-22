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
            +"현재 해시태그, 카테고리와는 연결 완료")
    public ResponseEntity<Boolean> register(@RequestBody registerRequest input, HttpServletRequest req) throws NotFoundException{
        return ResponseEntity.ok().body(challengeService.register(input, req));
    }

    @PostMapping("/test/{chlNum}")
    @ApiOperation(value = "mappedBy", notes = "이후 삭제 예정")
    public ResponseEntity<List<ChlTag>> test(@PathVariable("chlNum") Long chlNum) throws Exception{
        return ResponseEntity.ok().body(challengeService.test(chlNum));
    }
}
