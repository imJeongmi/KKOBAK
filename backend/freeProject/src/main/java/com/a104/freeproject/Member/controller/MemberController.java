package com.a104.freeproject.Member.controller;

import com.a104.freeproject.Member.service.MemberServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@ApiOperation(value = "[유저] Member Controller")
public class MemberController {

    private final MemberServiceImpl memberService;

    @PostMapping("/join")
    @ApiOperation(value="회원가입", notes = "security 얹으면서 변경 예정. 구조 제공 용도")
    public void join() {
        memberService.join();
    }

    @PostMapping("/chk-email")
    @ApiOperation(value="이메일 형식 체크", notes = "이메일 형식이 맞는지 boolean 형식으로 return")
    public boolean emailCheck(@RequestBody String email){
        return Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", email);
    }

    @PostMapping("/chk-pw")
    @ApiOperation(value="비밀번호 체크",
            notes =  "비밀번호 형식: 8-20자, 숫자/특수문자($`~!@$!%*#^?&()_=+)/영문자 필수 >> boolean 형식으로 return")
    public boolean pwCheck(@RequestBody String pw){
        return Pattern.matches("^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,20}$" , pw);
    }

}
