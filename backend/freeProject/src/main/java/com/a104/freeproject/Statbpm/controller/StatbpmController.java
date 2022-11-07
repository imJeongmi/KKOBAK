package com.a104.freeproject.Statbpm.controller;

import com.a104.freeproject.Member.request.JoinRequest;
import com.a104.freeproject.Member.response.TokenResponse;
import com.a104.freeproject.Statbpm.request.BpmInputRequest;
import com.a104.freeproject.Statbpm.service.StatbpmService;
import com.a104.freeproject.Statbpm.service.StatbpmServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
