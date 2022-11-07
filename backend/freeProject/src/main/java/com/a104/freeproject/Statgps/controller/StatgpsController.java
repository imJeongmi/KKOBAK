package com.a104.freeproject.Statgps.controller;

import com.a104.freeproject.Statgps.request.GpsInputRequest;
import com.a104.freeproject.Statgps.response.ResultResponse;
import com.a104.freeproject.Statgps.service.StatgpsServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @GetMapping("/list/{year}/{month}/{day}/{cid}")
    @ApiOperation(value="데이터 시도 목록 반환", notes = "'/api/gps/list/2022/11/07/1' 형식으로 사용")
    public ResponseEntity<ResultResponse> addData(@PathVariable("year") String year, @PathVariable("month") String month,
                                                        @PathVariable("day") String day, @PathVariable("cid") Long cid, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(statgpsService.getTryList(year, month, day, cid, req));
    }

}
