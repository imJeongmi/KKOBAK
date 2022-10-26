package com.a104.freeproject.Category.controller;

import com.a104.freeproject.Category.request.CategoryUpdateRequest;
import com.a104.freeproject.Category.request.CategoryRequest;
import com.a104.freeproject.Category.response.CategoryResponse;
import com.a104.freeproject.Category.service.CategoryServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@ApiOperation(value = "[카테고리] Category Controller", notes = "카테고리 삭제, 수정은 MM 주세용")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping("/reg")
    @ApiOperation(value = "[확인] 카테고리 등록",notes = "등록 완료 시 return true")
    public ResponseEntity<Boolean> regCategory(@RequestBody CategoryRequest input) throws NotFoundException {
        return ResponseEntity.ok().body(categoryService.regCategory(input));
    }

    @GetMapping("/list")
    @ApiOperation(value = "[확인] 카테고리 목록 불러오기")
    public ResponseEntity<List<CategoryResponse>> getList() throws NotFoundException {
        return ResponseEntity.ok().body(categoryService.getList());
    }

    @PatchMapping("/update")
    @ApiOperation(value = "카테고리 내용 수정", notes = "id와 수정할 name & imgurl을 다 넘겨주세요")
    public ResponseEntity<Boolean> updateCategory(@RequestBody CategoryUpdateRequest input) throws NotFoundException{
        return ResponseEntity.ok().body(categoryService.updateCategory(input));
    }
    // 챌린지 완성 시 카테고리별 챌린지 불러오는 API 작성하기

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value="카테고리 내용 삭제")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("id") Long id) throws NotFoundException{
        return ResponseEntity.ok().body(categoryService.deleteCategory(id));
    }
}
