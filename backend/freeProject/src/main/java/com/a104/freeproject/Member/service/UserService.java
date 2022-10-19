package com.a104.freeproject.Member.service;

import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.repository.MemberRepository;
import com.a104.freeproject.Member.response.MemberResponse;
import com.a104.freeproject.Member.util.SecurityUtil;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;

    public MemberResponse getMemberInfo(String email) throws NotFoundException {

        Member member = memberRepository.findByEmail(email);
        if(member==null || member.isDelete()){
            throw new NotFoundException("유효한 회원이 아닙니다.");
        }

        MemberResponse responseDto = new MemberResponse(email);
        return responseDto;
    }

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    public MemberResponse getMyInfo() throws NotFoundException {

        Member member = memberRepository.findByEmail(SecurityUtil.getCurrentMemberId());
        if(member==null || member.isDelete()){
            throw new NotFoundException("로그인 유저 정보가 없습니다.");
        }

        MemberResponse responseDto = new MemberResponse(member.getEmail());
        return responseDto;
    }

}
