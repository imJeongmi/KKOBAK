package com.a104.freeproject.PrtChl.repository;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrtChlRepository extends JpaRepository<PrtChl,Long> {
    boolean existsByChallengeAndMember(Challenge c, Member m);
    PrtChl findByChallengeAndMember(Challenge c, Member m);
}