package com.a104.freeproject.Member.service;

import com.a104.freeproject.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public void join() {
        // test. 이따 security 얹으면서 변경 예정
        System.out.println("test");
    }
}
