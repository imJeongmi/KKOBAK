package com.a104.freeproject.Log.controller;

import com.a104.freeproject.Challenge.request.registerRequest;
import com.a104.freeproject.Log.request.ChangeStatusRequest;
import com.a104.freeproject.Log.service.LogServiceImpl;
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
@RequestMapping("/log")
@RequiredArgsConstructor
@ApiOperation(value="[챌린지 로그] Log Controller")
public class LogController {

    private final LogServiceImpl logService;

    @PostMapping("/change-status")
    @ApiOperation(value = "[확인] 특정 일의 챌린지 수행여부 변경", notes = "return 값 변경 원하시면 MM 주세용\n")
    public ResponseEntity<Boolean> changeStatus(@RequestBody ChangeStatusRequest input, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(logService.changeTodoList(input, req));
    }

}
