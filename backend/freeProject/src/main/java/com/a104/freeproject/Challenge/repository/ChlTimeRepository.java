package com.a104.freeproject.Challenge.repository;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.entity.ChlTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChlTimeRepository extends JpaRepository<ChlTime,Long> {
    boolean existsByChallenge(Challenge c);
    ChlTime findByChallenge(Challenge c);
}
