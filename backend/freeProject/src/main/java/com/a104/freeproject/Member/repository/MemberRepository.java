package com.a104.freeproject.Member.repository;

import com.a104.freeproject.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByTag(String tag);
    boolean existsByHp(String hp);
    Member findByHp(String hp);
    Member findByNickname(String nickname);

}
