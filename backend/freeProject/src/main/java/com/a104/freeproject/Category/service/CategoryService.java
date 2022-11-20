package com.a104.freeproject.Category.service;

import com.a104.freeproject.Category.request.CategoryUpdateRequest;
import com.a104.freeproject.Category.request.CategoryRequest;
import com.a104.freeproject.Category.response.CategoryResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import java.util.List;

public interface CategoryService {

    boolean regCategory(CategoryRequest input) throws NotFoundException;
    List<CategoryResponse> getList();

    boolean updateCategory(CategoryUpdateRequest input) throws NotFoundException;

    boolean deleteCategory(Long id) throws NotFoundException;
}
