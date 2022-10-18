package com.a104.freeproject.Member.controller;

import com.a104.freeproject.Member.request.JoinRequest;
import com.a104.freeproject.Member.request.LoginRequest;
import com.a104.freeproject.Member.response.TokenResponse;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@ApiOperation(value = "[유저] Member Controller")
public class MemberController {

    private final MemberServiceImpl memberService;

    @PostMapping("/join")
    @ApiOperation(value="회원가입", notes = "이메일, 비밀번호, 닉네임, 핸드폰 번호 입력하기")
    public ResponseEntity<TokenResponse> join(@RequestBody JoinRequest input) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.join(input));
    }

    @PostMapping("/login")
    @ApiOperation(value="로그인", notes = "이메일, 패스워드 입력 필요")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest input, HttpSession session) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.login(input));
    }

    @PostMapping("/chk-email")
    @ApiOperation(value="이메일 형식/중복 체크", notes = "이메일 형식/중복 체크가 맞는지 boolean 형식으로 return (문제가 있으면 return false)")
    public boolean emailCheck(@RequestBody String email, HttpServletRequest req){
        return memberService.emailCheck(email);
    }

    @PostMapping("/chk-pw")
    @ApiOperation(value="비밀번호 체크",
            notes =  "비밀번호 형식: 8-20자, 숫자/특수문자($`~!@$!%*#^?&()_=+)/영문자 필수 >> boolean 형식으로 return (형식에 잘 맞으면 return true)")
    public boolean pwCheck(@RequestBody String pw, HttpServletRequest req){
        return memberService.pwCheck(pw);
    }

    @PostMapping("/chk-name")
    @ApiOperation(value="닉네임 중복 체크", notes =  "닉네임 중복 여부 boolean 형식으로 return (중복 >> return false)")
    public boolean nameCheck(@RequestBody String name, HttpServletRequest req){
        return memberService.nameCheck(name);
    }

}
