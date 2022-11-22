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

import java.util.List;

@RestController
@RequestMapping("/heart")
@RequiredArgsConstructor
@ApiOperation(value="[심박수 로그] Heart Controller")
public class HeartController {

    @PostMapping("/one")
    @ApiOperation(value = "심장 한개만 받아보기")
    public boolean printOne(@RequestBody int heartRateReq){
        System.out.println("심장이 뛴다: "+heartRateReq);
        return true;
    }

    @PostMapping("/list")
    @ApiOperation(value = "심장 여러개 받아보기")
    public boolean printList(@RequestBody List<Integer> heartRateListReq){
        for (int a : heartRateListReq) {
            System.out.println("심장이 뛴다: "+a);
        }
        return true;
    }
}
