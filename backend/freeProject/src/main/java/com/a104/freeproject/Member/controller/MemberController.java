package com.a104.freeproject.Member.controller;

import com.a104.freeproject.Member.request.*;
import com.a104.freeproject.Member.response.AuthNumResponse;
import com.a104.freeproject.Member.response.EmailResponse;
import com.a104.freeproject.Member.response.MyInfoResponse;
import com.a104.freeproject.Member.response.TokenResponse;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.Member.service.MmsServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@ApiOperation(value = "[유저] Member Controller",
        notes = "개인정보라 대부분 post 처리 했어요~ 수정 원하시면 MM 주세용\n"
                +"access token의 경우 제가 보내는건 accessToken으로 보내요.\n"
                +"헤더에 담아서 보내주실 때는 Authorization으로 보내주세용. 이것도 수정 원하시면 MM 주세용")
public class MemberController {

    private final MemberServiceImpl memberService;
    private final MmsServiceImpl mmsService;

    @PostMapping("/join")
    @ApiOperation(value="[확인] 회원가입", notes = "이메일, 비밀번호, 닉네임, 핸드폰 번호 입력하기, 토큰: accessToken")
    public ResponseEntity<TokenResponse> join(@RequestBody JoinRequest input) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.join(input));
    }

    @PostMapping("/login")
    @ApiOperation(value="[확인] 로그인", notes = "이메일, 패스워드 입력 필요, 토큰: accessToken")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest input) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.login(input));
    }

    @PostMapping("/chk-email")
    @ApiOperation(value="[확인] 이메일 형식/중복 체크", notes = "이메일 형식/중복 체크가 맞는지 boolean 형식으로 return (문제가 있으면 return false)")
    public boolean emailCheck(@RequestBody String email){
        return memberService.emailCheck(email);
    }

    @PostMapping("/chk-pw")
    @ApiOperation(value="[확인] 비밀번호 체크",
            notes =  "비밀번호 형식: 8-20자, 숫자/특수문자($`~!@$!%*#^?&()_=+)/영문자 필수 >> boolean 형식으로 return (형식에 잘 맞으면 return true)")
    public boolean pwCheck(@RequestBody String pw, HttpServletRequest req){
        return memberService.pwCheck(pw);
    }

    @PostMapping("/chk-name")
    @ApiOperation(value="[확인] 닉네임 중복 체크", notes =  "닉네임 중복 여부 boolean 형식으로 return (중복 >> return false, 사용가능한 닉네임 >> return true)")
    public boolean nameCheck(@RequestBody String name, HttpServletRequest req){
        return memberService.nameCheck(name);
    }

    @PatchMapping("/register/watch")
    @ApiOperation(value="[확인] 갤럭시 워치 등록", notes = "워치 시리얼 넘버라고 생각하고 우선 string 값 저장한다고 생각. 현재는 성공 시 return true")
    public ResponseEntity<Boolean> join(@RequestBody WatchRequest input, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.regWatch(input,req));
    }

    @PostMapping("/register/phone")
    @ApiOperation(value="[확인] 번호 인증", notes = "phoneNumber 형식: 01012345678")
    public ResponseEntity<AuthNumResponse> sendMMS(@RequestBody MmsRequest input) throws NotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException {
        return ResponseEntity.ok().body(mmsService.sendMMS(input));
    }

    @PatchMapping("/change/nick")
    @ApiOperation(value="[확인] 닉네임 변경")
    public ResponseEntity<Boolean> changeNick(@RequestBody NickRequest input, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.changeNick(input, req));
    }

    @PatchMapping("/change/phone")
    @ApiOperation(value="[확인] 핸드폰 번호 변경. 먼저 문자 보내서 인증번호 확인하기")
    public ResponseEntity<Boolean> changePhone(@RequestBody PhoneRequest input, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.changePhone(input, req));
    }

    @PatchMapping("/del/watch")
    @ApiOperation(value="[확인] 워치 삭제 >> 그냥 워치 현재 정보 받지 말고 토큰으로 내정보만 받아서 날릴까요? 의견 말씀해주세용", notes = "현재는 워치 시리얼 넘버라고 생각")
    public ResponseEntity<Boolean> delWatch (@RequestBody WatchRequest input, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.delWatch(input, req));
    }

    @GetMapping("/my-info")
    @ApiOperation(value="[확인] 토큰으로 내 정보 받기", notes = "return 닉네임, 이메일")
    public ResponseEntity<MyInfoResponse> getMyInfo (HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.getMyInfo(req));
    }

    @PostMapping("/user-info")
    @ApiOperation(value="[확인] 다른 유저 닉네임으로 이메일 받기", notes = "닉네임이 한글일 수 있어 PostMapping")
    public ResponseEntity<EmailResponse> getUserInfo (@RequestBody NickRequest input) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.getUserInfo(input));
    }
}
