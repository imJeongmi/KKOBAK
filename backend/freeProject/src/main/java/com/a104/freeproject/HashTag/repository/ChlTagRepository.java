package com.a104.freeproject.HashTag.repository;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.HashTag.entity.ChlTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChlTagRepository extends JpaRepository<ChlTag,Long> {
    List<Challenge> findByHashtagId(Long hashTagId);
}
