package com.a104.freeproject.PrtChl.controller;

import com.a104.freeproject.Challenge.request.registerRequest;
import com.a104.freeproject.PrtChl.request.ChlRequest;
import com.a104.freeproject.PrtChl.response.MyChallengeDetailResponse;
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
    @PostMapping("/{chlId}/{alarm}")
    @ApiOperation(value = "[확인] 챌린지 참여", notes = "'/participate/1/4' 형식으로 사용.\n"
            +"alarm: 1 웹앱, 2 웹, 3 앱, 4 안받음 \n"
            +"return 값 변경 원하시면 MM 주세용\n")
    public ResponseEntity<Boolean> register(@PathVariable("chlId") Long cid, @PathVariable("alarm") int alarm, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(prtChlService.participate(cid, req, alarm));
    }

    @PostMapping("/drop/{chlId}")
    @ApiOperation(value = "챌린지 참여중단(시간 되어서 마감한게 아니라 유저가 걍 마감쳤을 때)", notes = "'/participate/drop/4' 형식으로 사용.\n"
            +"return 값 변경 원하시면 MM 주세용\n")
    public ResponseEntity<Boolean> dropChl(@PathVariable("chlId") Long cid, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(prtChlService.dropChl(cid, req));
    }

    @PatchMapping("/change/kkobak")
    @ApiOperation(value = "참여한 꼬박 챌린지 상태 변경")
    public ResponseEntity<Boolean> changeKkobak(@RequestBody ChlRequest chlRequest, HttpServletRequest req) throws NotFoundException{
        return ResponseEntity.ok().body(prtChlService.changeKkobak(chlRequest,req));
    }

    @GetMapping("/detail/{chlId}")
    @ApiOperation(value = "내가 참가한 특정 챌린지의 상세 정보를 반환")
    public ResponseEntity<MyChallengeDetailResponse> getPtrChlDetail(@PathVariable("chlId")Long cid, HttpServletRequest req) throws NotFoundException{
        return ResponseEntity.ok().body(prtChlService.getPtrChlDetail(cid,req));
    }
    // 스케줄러 돌려서 알람 보내는거 작성

}
