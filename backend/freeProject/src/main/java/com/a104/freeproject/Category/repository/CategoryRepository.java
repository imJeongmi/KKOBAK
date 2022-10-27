package com.a104.freeproject.Category.repository;

import com.a104.freeproject.Category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsByName(String name);
    Category findByName(String name);

}
