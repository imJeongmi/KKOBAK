package com.a104.freeproject.PrtChl.controller;

import com.a104.freeproject.Challenge.request.registerRequest;
import com.a104.freeproject.PrtChl.service.PrtChlServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/participate")
@RequiredArgsConstructor
@ApiOperation(value="[챌린지 별 참여한 유저] PrtChl Controller")
public class PrtChlController {

    private final PrtChlServiceImpl prtChlService;

    // 참여
    @PostMapping("/{chlId}")
    @ApiOperation(value = "[확인] 챌린지 참여", notes = "return 값 변경 원하시면 MM 주세용\n")
    public ResponseEntity<Boolean> register(@PathVariable("chlId") Long cid, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(prtChlService.participate(cid, req));
    }

    // 완료 여부 변경 (시간 되어서 마감한게 아니라 유저가 걍 마감쳤을 때)

    // 스케쥴러 돌려서 종료일 되면 알아서 마감되게 하는거 작성

    // 스케쥴러 돌려서 참여일 되면 알아서 챌린지 로그 생성되는 api 작성

}
