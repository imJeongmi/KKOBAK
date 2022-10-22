package com.a104.freeproject.Category.service;

import com.a104.freeproject.Category.response.DetailCategoryResponse;
import com.a104.freeproject.Category.response.DetailResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import java.util.List;

public interface DetailCategoryService {
    boolean regDetail(Long category, String name) throws NotFoundException;
    List<DetailResponse> getCategoryList(Long category) throws NotFoundException;
    List<DetailCategoryResponse> getAllCategoryList() throws NotFoundException;
}
