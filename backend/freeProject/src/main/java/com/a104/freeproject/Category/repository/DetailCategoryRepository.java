package com.a104.freeproject.Category.repository;

import com.a104.freeproject.Category.entity.Category;
import com.a104.freeproject.Category.entity.DetailCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailCategoryRepository extends JpaRepository<DetailCategory,Long> {
    boolean existsByName(String name);
    boolean existsByCategoryAndName(Category category, String name);
    List<DetailCategory> findAllByCategory(Category category);
}
