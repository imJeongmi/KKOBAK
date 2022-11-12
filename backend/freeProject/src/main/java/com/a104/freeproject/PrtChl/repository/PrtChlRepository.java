package com.a104.freeproject.PrtChl.repository;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.Statgps.entity.Statgps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PrtChlRepository extends JpaRepository<PrtChl,Long> {
    boolean existsByChallengeAndMember(Challenge c, Member m);
    PrtChl findByChallengeAndMember(Challenge c, Member m);
    @Query(value="select * from prt_chl where is_fin=false and `member_id`=:m", nativeQuery = true)
    List<PrtChl> findByMember(@Param("m") Member m, Pageable pageable);
    @Query(value="select * from prt_chl where is_fin=false and `member_id`=:m", nativeQuery = true)
    Page<PrtChl> findByMember(Pageable pageable,@Param("m")  Member m);
    @Query(value="select * from prt_chl where is_fin=false and `member_id`=:m", nativeQuery = true)
    List<PrtChl> findByMember(@Param("m") Member m);

    @Query(value="select * from prt_chl as prtchl where is_fin=false and member_id=:m and (select watch from challenge where id = prtchl.challenge_id)=:useWatch", nativeQuery = true)
    List<PrtChl> findAllByWatchAndMember(boolean useWatch, Pageable pageable, Member m);

    @Query(value="select * from prt_chl as prtchl where is_fin=false and member_id=:m and (select watch from challenge where id = prtchl.challenge_id)=:useWatch", nativeQuery = true)
    List<PrtChl> findAllByWatchAndMember(Member m, boolean useWatch);

}