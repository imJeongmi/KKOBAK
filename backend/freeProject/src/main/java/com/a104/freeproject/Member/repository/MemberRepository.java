package com.a104.freeproject.Member.repository;

import com.a104.freeproject.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
