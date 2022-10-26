package com.a104.freeproject.PrtChl.repository;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrtChlRepository extends JpaRepository<PrtChl,Long> {
    boolean existsByChallengeAndMember(Challenge c, Member m);
    PrtChl findByChallengeAndMember(Challenge c, Member m);
    List<PrtChl> findByMember(Member m, Pageable pageable);
    Page<PrtChl> findByMember(Pageable pageable, Member m);
}