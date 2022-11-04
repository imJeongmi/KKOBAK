package com.a104.freeproject.Log.controller;

import com.a104.freeproject.Log.request.HeartRateListReq;
import com.a104.freeproject.Log.request.HeartRateReq;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heart")
@RequiredArgsConstructor
@ApiOperation(value="[심박수 로그] Heart Controller")
public class HeartController {

    @PostMapping("/one")
    public ResponseEntity<Boolean> printOne(@RequestBody HeartRateReq heartRateReq){
        System.out.println("심장이 뛴다: "+heartRateReq.getHeartRate());
        return ResponseEntity.ok().body(true);
    }

    @PostMapping("/list")
    public ResponseEntity<Boolean> printList(@RequestBody HeartRateListReq heartRateListReq){
        for (int a : heartRateListReq.getHeartRateList()) {
            System.out.println("심장이 뛴다: "+a);
        }
        return ResponseEntity.ok().body(true);
    }
}