package com.a104.freeproject.Category.service;

import com.a104.freeproject.Category.response.CategoryResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import java.util.List;

public interface CategoryService {

    boolean regCategory(String name) throws NotFoundException;
    List<CategoryResponse> getList();

}
