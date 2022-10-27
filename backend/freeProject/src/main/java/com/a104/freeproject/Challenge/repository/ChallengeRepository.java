package com.a104.freeproject.Challenge.repository;

import com.a104.freeproject.Challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge,Long> {
    List<Challenge> findAllByStatus(int status);

}
