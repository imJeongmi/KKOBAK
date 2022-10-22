package com.a104.freeproject.Category.repository;

import com.a104.freeproject.Category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsByName(String name);

}
