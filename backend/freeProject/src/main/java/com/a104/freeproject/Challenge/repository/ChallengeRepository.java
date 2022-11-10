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

    @Query(value="select * from challenge where is_fin=false and category_id=:categoryId", nativeQuery = true)
    Page<Challenge> findAllByCategoryId(Long categoryId, PageRequest pageRequest);

    @Query(value="select * from challenge where is_fin=false and detail_category_id=:detailCategoryId", nativeQuery = true)
    Page<Challenge> findAllByDetailCategoryId(Long detailCategoryId, PageRequest pageRequest);

    @Query(value="select * from challenge where is_fin=false and title like %:word% ", nativeQuery = true)
    Page<Challenge> findByTitleContains(String word, PageRequest pageRequest);

    @Query(value="select * from challenge where is_fin=false and writer=:writerId", nativeQuery = true)
    Page<Challenge> findByWriter(Long writerId, PageRequest pageRequest);

    @Query(value="select * from challenge where is_fin=false and id in :challengeIdList", nativeQuery = true)
    Page<Challenge> findByIdIn(List<Long> challengeIdList, PageRequest pageRequest);

    @Query(value="select * from challenge where is_fin=false", nativeQuery = true)
    Page<Challenge> findAllChallengeSet(Pageable pageable);

    @Query(value="select * from challenge where is_fin=false", nativeQuery = true)
    Page<Challenge> findAllChallenge(PageRequest pageRequest);

}
