package com.a104.freeproject.Statgps.controller;

import com.a104.freeproject.Statgps.request.GpsInputRequest;
import com.a104.freeproject.Statgps.service.StatgpsServiceImpl;
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
@RequestMapping("/gps")
@RequiredArgsConstructor
@ApiOperation(value="[GPS] Statgps Controller")
public class StatgpsController {

    private final StatgpsServiceImpl statgpsService;

    @PostMapping("/add")
    @ApiOperation(value="[확인] 데이터 입력", notes = "")
    public ResponseEntity<Boolean> addData(@RequestBody GpsInputRequest input, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(statgpsService.addData(input, req));
    }

}
