package com.a104.freeproject.HashTag.repository;

import com.a104.freeproject.HashTag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {
    boolean existsByName(String name);
    Hashtag findByName(String name);
}
