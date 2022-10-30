package com.a104.freeproject.HashTag.controller;

import com.a104.freeproject.Category.request.CategoryNameRequest;
import com.a104.freeproject.HashTag.request.HashtagInputRequest;
import com.a104.freeproject.HashTag.service.HashtagServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hashtag")
@RequiredArgsConstructor
@ApiOperation(value = "[태그] Hashtag Controller",
        notes = "수정, 삭제, 리스트 불러오기는 MM 주세용 + 읽기는 필요 없을 것 같아서 추가 안함. 추후 필요 없는 컨트롤러라면 삭제 예정")
public class HashtagController {

    private final HashtagServiceImpl hashtagService;

    //등록
    @PostMapping("/reg")
    @ApiOperation(value = "[확인] tag 등록",notes = "등록 완료 시 return true")
    public ResponseEntity<Boolean> regTag(@RequestBody HashtagInputRequest input, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(hashtagService.registerTag(input.getTagList()));
    }

    // 챌린지 추가 후 chl_tag 추가
    @PostMapping(value = "/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ApiOperation(value="이미지 전송시 AI 태그 반환")
    public ResponseEntity<?> getTagsByImage(@RequestPart(value = "file") MultipartFile multipartFile) throws Exception{
        Map<String, List<String>> result = hashtagService.getTagsByImage(multipartFile) ;
        return (new ResponseEntity<Map<String, List<String>>>(result, HttpStatus.OK));
    }

    @PostMapping(value = "/contents")
    @ApiOperation(value="이미지 전송시 AI 태그 반환")
    public ResponseEntity<?> getTagsByContents(@RequestBody String contents) throws Exception{
        Map<String, List<String>> result = hashtagService.getTagsByContents(contents) ;
        return (new ResponseEntity<Map<String, List<String>>>(result, HttpStatus.OK));
    }
}
