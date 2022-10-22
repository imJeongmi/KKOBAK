package com.a104.freeproject.Category.controller;

import com.a104.freeproject.Category.response.CategoryResponse;
import com.a104.freeproject.Category.service.CategoryServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@ApiOperation(value = "[카테고리] Category Controller")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping("/reg/{name}")
    @ApiOperation(value = "[확인] 카테고리 등록",notes = "'/category/reg/{name}' 형식으로 사용. 등록 완료 시 return true")
    public ResponseEntity<Boolean> regCategory(@PathVariable("name") String name) throws NotFoundException {
        return ResponseEntity.ok().body(categoryService.regCategory(name));
    }

    @GetMapping("/list")
    @ApiOperation(value = "[확인] 카테고리 목록 불러오기",notes = "'/category/list' 형식으로 사용.")
    public ResponseEntity<List<CategoryResponse>> getList() throws NotFoundException {
        return ResponseEntity.ok().body(categoryService.getList());
    }
}
