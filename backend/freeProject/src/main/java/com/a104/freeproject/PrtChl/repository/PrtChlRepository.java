package com.a104.freeproject.PrtChl.repository;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrtChlRepository extends JpaRepository<PrtChl,Long> {
    boolean existsByChallengeAndMember(Challenge c, Member m);
    PrtChl findByChallengeAndMember(Challenge c, Member m);
    @Query(value="select * from prt_chl where is_fin=false and `member_id`=:m", nativeQuery = true)
    List<PrtChl> findByMember(@Param("m") Member m, Pageable pageable);
    @Query(value="select * from prt_chl where is_fin=false and `member_id`=:m", nativeQuery = true)
    Page<PrtChl> findByMember(Pageable pageable,@Param("m")  Member m);
}