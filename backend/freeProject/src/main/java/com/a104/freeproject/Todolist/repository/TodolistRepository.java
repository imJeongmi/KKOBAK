package com.a104.freeproject.Todolist.repository;

import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.Todolist.entity.Todolist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodolistRepository extends JpaRepository<Todolist,Long> {
    boolean existsByContentsAndDate(String contents, LocalDate date);
    boolean existsByMemberAndDate(Member member, LocalDate date);
    List<Todolist> findAllByMemberAndDate(Member member, LocalDate date);
}
