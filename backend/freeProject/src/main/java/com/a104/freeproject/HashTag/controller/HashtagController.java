package com.a104.freeproject.HashTag.controller;

import com.a104.freeproject.Category.request.CategoryNameRequest;
import com.a104.freeproject.HashTag.request.HashtagInputRequest;
import com.a104.freeproject.HashTag.service.HashtagServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hashtag")
@RequiredArgsConstructor
@ApiOperation(value = "[태그] Hashtag Controller", notes = "수정, 삭제, 리스트 불러오기는 MM 주세용 + 읽기는 필요 없을 것 같아서 추가 안함")
public class HashtagController {

    private final HashtagServiceImpl hashtagService;

    //등록
    @PostMapping("/reg")
    @ApiOperation(value = "[확인] tag 등록",notes = "등록 완료 시 return true")
    public ResponseEntity<Boolean> regTag(@RequestBody HashtagInputRequest input) throws NotFoundException {
        return ResponseEntity.ok().body(hashtagService.registerTag(input.getTagList()));
    }

    // 챌린지 추가 후 chl_tag 추가
}
