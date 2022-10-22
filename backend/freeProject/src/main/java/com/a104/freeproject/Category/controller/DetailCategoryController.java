package com.a104.freeproject.Category.controller;

import com.a104.freeproject.Category.request.DetailNameRequest;
import com.a104.freeproject.Category.response.DetailCategoryResponse;
import com.a104.freeproject.Category.response.DetailResponse;
import com.a104.freeproject.Category.service.DetailCategoryServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detail-category")
@RequiredArgsConstructor
@ApiOperation(value = "[세부카테고리] DetailCategory Controller", notes = "세부카테고리 삭제, 수정은 MM 주세용")
public class DetailCategoryController {

    private final DetailCategoryServiceImpl detailService;

    @PostMapping("reg/{category}")
    @ApiOperation(value="[확인] 세부 카테고리 등록", notes = "'/detail-category/reg/1' 형식으로 사용. 등록 완료 시 return true")
    public ResponseEntity<Boolean> regDetail(@PathVariable("category") Long category, @RequestBody DetailNameRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(detailService.regDetail(category, req.getName()));
    }

    @GetMapping("list/{category}")
    @ApiOperation(value="[확인] 카테고리 별 리스트 return",
            notes = "'/detail-category/list/1' 형식으로 사용. return detailCategoryId, detailCategoryName")
    public ResponseEntity<List<DetailResponse>> getCategoryList(@PathVariable("category") Long category) throws NotFoundException {
        return ResponseEntity.ok().body(detailService.getCategoryList(category));
    }

    @GetMapping("/list")
    @ApiOperation(value="[확인] 전체 리스트 return")
    public ResponseEntity<List<DetailCategoryResponse>> getAllCategoryList() throws NotFoundException {
        return ResponseEntity.ok().body(detailService.getAllCategoryList());
    }

    // 추후 세부 카테고리별 챌린지 리스트 가져오는 api 추가해야함

}
