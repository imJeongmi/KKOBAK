package com.a104.freeproject.Challenge.repository;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge,Long> {
    List<Challenge> findAllByStatus(int status);

    Page<Challenge> findAllByCategoryId(Long categoryId, PageRequest pageRequest);

    Page<Challenge> findAllByDetailCategoryId(Long detailCategoryId, PageRequest pageRequest);

    Page<Challenge> findByTitleContains(String word, PageRequest pageRequest);

    Page<Challenge> findByWriter(Long writerId, PageRequest pageRequest);

    Page<Challenge> findByIdIn(List<Long> challengeIdList, PageRequest pageRequest);

    @Query(value="select * from challenge where is_fin=false", nativeQuery = true)
    Page<Challenge> findAllChallengeSet(Pageable pageable);

    @Query(value="select * from challenge where is_fin=false", nativeQuery = true)
    Page<Challenge> findAllChallenge(PageRequest pageRequest);

    @Query(value="select * from challenge where watch=:useWatch and is_fin=false", nativeQuery = true)
    List<Challenge> findAllByWatch(boolean useWatch, Pageable pageable);
}
