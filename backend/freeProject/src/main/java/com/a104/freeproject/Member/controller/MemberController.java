package com.a104.freeproject.Member.controller;

import com.a104.freeproject.Challenge.response.ChlSimpleResponse;
import com.a104.freeproject.Member.request.*;
import com.a104.freeproject.Member.response.*;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.Member.service.MmsServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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
    public boolean pwCheck(@RequestBody String pw){
        return memberService.pwCheck(pw);
    }

    @PostMapping("/chk-name")
    @ApiOperation(value="[확인] 닉네임 중복 체크", notes =  "닉네임 중복 여부 boolean 형식으로 return (중복 >> return false, 사용가능한 닉네임 >> return true)")
    public boolean nameCheck(@RequestBody String name){
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
    @ApiOperation(value="[확인] 핸드폰 번호 변경. 먼저 문자 보내서 인증번호 확인하기", notes = "phoneNumber 형식: 01012345678")
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
    public ResponseEntity<EmailResponse> getUserInfo (@RequestBody NickRequest input, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.getUserInfo(input));
    }

    @GetMapping("/my-chl-list")
    @ApiOperation(value="[확인] 내가 참여한 챌린지 목록 받기", notes = "'/member/my-chl-list?page=0&size=3&sort=id,DESC' 형식으로 사용.\n"
            + "이 api는 스웨거에서 페이지랑 사이즈 조절 불가. 원하는 데이터 있으면 백엔드 문의해주세용\n"
            + "return 변경 원하시면 MM주세용~")
    public ResponseEntity<List<ChlSimpleResponse>> getMyChallenge (Pageable pageable, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.getUserChallenge(pageable, req));
    }

    @GetMapping("/sum/my-chl-list")
    @ApiOperation(value="[확인] 내가 참여한 챌린지 page 수 받기", notes = "'/member/sum/my-chl-list?size=3&sort=id,DESC' 형식으로 사용.\n"
            + "이 api는 스웨거에서 페이지랑 사이즈 조절 불가. 원하는 데이터 있으면 백엔드 문의해주세용\n"
            + "size 갯수만큼 페이지를 만든다면 몇 페이지 까지 만들 수 있는지 return")
    public ResponseEntity<Integer> getMyChlTotalSum (Pageable pageable, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.getMyChlTotalSum(pageable, req));
    }

    // 다른 사람 닉네임으로 참여한 챌린지 목록 받기
    @PostMapping("/user-chl-list")
    @ApiOperation(value="[확인] 다른 사람이 참여한 챌린지 목록 받기", notes = "'/member/user-chl-list?page=0&size=3&sort=id,DESC' 형식으로 사용.\n"
            + "이 api는 스웨거에서 페이지랑 사이즈 조절 불가. 원하는 데이터 있으면 백엔드 문의해주세용\n"
            + "return 변경 원하시면 MM주세용~")
    public ResponseEntity<List<ChlSimpleResponse>> getUserChallenge (@RequestBody NickRequest input,Pageable pageable, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.getMemberChallenge(pageable, input));
    }

    @PostMapping("/sum/user-chl-list")
    @ApiOperation(value="[확인] 다른 사람이 참여한 챌린지 page 수 받기", notes = "'/member/sum/user-chl-list?size=3&sort=id,DESC' 형식으로 사용.\n"
            + "이 api는 스웨거에서 페이지랑 사이즈 조절 불가. 원하는 데이터 있으면 백엔드 문의해주세용\n"
            + "size 갯수만큼 페이지를 만든다면 몇 페이지 까지 만들 수 있는지 return")
    public ResponseEntity<Integer> getUserChlTotalSum (@RequestBody NickRequest input,Pageable pageable, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.getUserChlTotalSum(pageable, input));
    }

    @GetMapping("/chl-info")
    @ApiOperation(value="[확인] 챌린지 간단 통계(참여, 진행중, 완료, 성공, 실패한 챌린지 갯수)",
            notes = "다른 사람건 일단 안만들게요 필요하면 말해주세용\n"
                    +"totalChl 참여한 챌린지 수, ingChl 진행중인 챌린지 수, finChl 완료한 챌린지 수, sucChl 성공한 챌린지 수, failChl 실패한 챌린지 수")
    public ResponseEntity<ChlSimpleStatResponse> getChlSimpleStatistics (HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.getChlSimpleStatistics(req));
    }

    @GetMapping("/month-info/{chlId}/{year}/{month}")
    @ApiOperation(value="[확인] 챌린지 별 달 전체 로그 뽑아오기",
            notes = "'/member/month-chl/2/2022/10' 형식으로 사용.\n"+
                    "다른 사람건 일단 안만들게요 필요하면 말해주세용 + return 데이터 수정 필요하면 말해주세용\n")
    public ResponseEntity<List<MonthChlResponse>> monthChlInfo (@PathVariable("chlId") Long chlId, @PathVariable("year") String year,
                                                                @PathVariable("month") String month, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.monthChlInfo(chlId, year, month, req));
    }

    @PostMapping("/todo-list/info")
    @ApiOperation(value="[확인] (todo-list) 일자별 본인 챌린지 목록 및 실행 여부 가져오기\n"
            +"'/member/todo-list/info' 형식으로 사용. \n"
            + "'2022-03-03' 형식으로 날짜 입력해주세용")
    public ResponseEntity<List<TodoListInfoResponse>> getTodoListInfo (@RequestBody DayRequest day,HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.getTodoListInfo(day, req));
    }
}
