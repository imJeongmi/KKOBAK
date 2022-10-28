package com.a104.freeproject.Challenge.repository;

import com.a104.freeproject.Challenge.entity.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge,Long> {
    List<Challenge> findAllByStatus(int status);

    Page<Challenge> findAllByCategoryId(Long categoryId, PageRequest pageRequest);

    Page<Challenge> findAllByDetailCategoryId(Long detailCategoryId, PageRequest pageRequest);

    Page<Challenge> findByTitleContains(String word, PageRequest pageRequest);
}
