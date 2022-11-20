package com.a104.freeproject.Member.response;

import com.a104.freeproject.Member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    private String email;

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getEmail());
    }
}