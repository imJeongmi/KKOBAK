package com.a104.freeproject.Statbpm.controller;

import com.a104.freeproject.Statbpm.request.BpmInputRequest;
import com.a104.freeproject.Statbpm.response.BpmResultResponse;
import com.a104.freeproject.Statbpm.service.StatbpmServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bpm")
@RequiredArgsConstructor
@ApiOperation(value="[BPM] Statbpm Controller")
public class StatbpmController {

    private final StatbpmServiceImpl statbpmService;

    @PostMapping("/add")
    @ApiOperation(value="[확인] 데이터 입력", notes = "")
    public ResponseEntity<Boolean> addData(@RequestBody BpmInputRequest input, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(statbpmService.addData(input, req));
    }

    @GetMapping("/list/{year}/{month}/{day}/{cid}")
    @ApiOperation(value="데이터 시도 목록 반환", notes = "'/api/bpm/list/2022/11/07/1' 형식으로 사용")
    public ResponseEntity<BpmResultResponse> addData(@PathVariable("year") String year, @PathVariable("month") String month,
                                                     @PathVariable("day") String day, @PathVariable("cid") Long cid, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(statbpmService.getTryList(year, month, day, cid, req));
    }

}
