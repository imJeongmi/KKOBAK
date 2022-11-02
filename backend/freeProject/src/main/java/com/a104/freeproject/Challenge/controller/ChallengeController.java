package com.a104.freeproject.Challenge.controller;

import com.a104.freeproject.Challenge.request.*;
import com.a104.freeproject.Challenge.response.ChallengeListResponse;
import com.a104.freeproject.Challenge.response.ChlUserNameResponse;
import com.a104.freeproject.Challenge.response.ChlUserSimpleStatResponse;
import com.a104.freeproject.Challenge.service.ChallengeServiceImpl;
import com.a104.freeproject.Member.request.NickRequest;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
@ApiOperation(value="[챌린지] Challenge Controller")
public class ChallengeController {

    private final ChallengeServiceImpl challengeService;

    @PostMapping("/register")
    @ApiOperation(value = "[확인] 챌린지 등록", notes = "return 값 변경 원하시면 MM 주세용\n"
            +"순서 >> 회원가입 >> 토큰 입력(유효기간 7일) >> 카테고리 생성 + 세부카테고리 생성(있으면 pass) >> 이미지url 생성 >> 챌린지 생성 일..걸요...?"
            +"startTime, endTime 걍 스웨거 무시하시고 \"startTime\": \"2022-10-22T22:37\" 형식으로 입력해주시면 됩니당\n"
            +"알람시간은 24시간 표시 형식으로 >> 14:25 이렇게 보내주세용")
    public ResponseEntity<Boolean> register(@RequestBody registerRequest input, HttpServletRequest req) throws NotFoundException{
        System.out.println("처음 들어온 값 controller >> "+input.getStartTime());
        return ResponseEntity.ok().body(challengeService.register(input, req));
    }

    @GetMapping("/user-list/{chlId}")
    @ApiOperation(value="[확인] 챌린지별 참여한 유저 목록 가져오기", notes = "'/challenge/user-list/2' 형식으로 사용.")
    public ResponseEntity<List<ChlUserNameResponse>> getUserInfo (@PathVariable("chlId") Long chlId, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(challengeService.getUserInfo(chlId));
    }

    @GetMapping("/user-list/stat/{chlId}")
    @ApiOperation(value="[확인] 챌린지별 참여한 유저 닉네임, 비율(성공) 리스트 가져오기", notes = "'/challenge/user-list/2' 형식으로 사용.\n"
            +"성공 비율의 경우 소수점 셋째 자리까지 나오게 표현.")
    public ResponseEntity<List<ChlUserSimpleStatResponse>> getUserSimpleStatInfo (@PathVariable("chlId") Long chlId, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(challengeService.getUserSimpleStatInfo(chlId));
    }

    @GetMapping("/list/{page}")
    @ApiOperation(value="챌린지 리스트 페이지네이션으로 반환", notes = "목록에 무슨 데이터가 필요한지 몰라서 일단 테이블에 있는거만 가져옵니다, 데이터 없는거 말해주새요 수정 예정")
    public ResponseEntity<List<ChallengeListResponse>> getChallengePageList(@PathVariable("page") int page) throws NotFoundException{
        return ResponseEntity.ok().body(challengeService.getChallengePageList(page));
    }

    @GetMapping("/list/page-cnt")
    @ApiOperation(value="[확인] 전체 챌린지 목록 총 페이지", notes = "'/api/challenge/list/page-cnt?size=3&&sort=id,DESC' 형식으로 사용")
    public ResponseEntity<Integer> getChallengePageCnt(Pageable pageable) throws NotFoundException{
        return ResponseEntity.ok().body(challengeService.getChallengePageCnt(pageable));
    }



    @GetMapping("/list/category/{categoryId}/{page}")
    @ApiOperation(value="카테고리별 챌린지 리스트 페이지", notes = "목록에 무슨 데이터가 필요한지 몰라서 일단 테이블에 있는거만 가져옵니다, 데이터 없는거 말해주새요 수정 예정")
    public ResponseEntity<List<ChallengeListResponse>> getChallengePageListByCategory(@PathVariable("page") int page, @PathVariable("categoryId") Long id,HttpServletRequest req) throws NotFoundException{
        return ResponseEntity.ok().body(challengeService.getChallengePageListByCategory(page,id));
    }

    @GetMapping("/list/detail/{detailCategoryId}/{page}")
    @ApiOperation(value="상세 카테고리별 챌린지 리스트 페이지", notes = "목록에 무슨 데이터가 필요한지 몰라서 일단 테이블에 있는거만 가져옵니다, 데이터 없는거 말해주새요 수정 예정")
    public ResponseEntity<List<ChallengeListResponse>> getChallengePageListByDetailCategory(@PathVariable("page") int page, @PathVariable("detailCategoryId") Long id, HttpServletRequest req) throws NotFoundException{
        return ResponseEntity.ok().body(challengeService.getChallengePageListByDetailCategory(page, id));
    }

    @GetMapping("/list/search/title/{page}")
    @ApiOperation(value="챌린지 리스트 제목으로 검색 페이지", notes = "제목에 단어가 포함되면 모두 검색")
    public ResponseEntity<List<ChallengeListResponse>> getChallengePageListByTitle(@PathVariable("page") int page, @RequestBody WordRequest input) throws NotFoundException{
        return ResponseEntity.ok().body(challengeService.getChallengePageListByTitle(page, input.getWord()));
    }

    @GetMapping("/list/search/nickname/{page}")
    @ApiOperation(value="챌린지 리스트 닉네임으로 검색 페이지", notes = "닉네임 정확히 일치해야 검색")
    public ResponseEntity<List<ChallengeListResponse>> getChallengePageListByNickName(@PathVariable("page") int page, @RequestBody NicknameRequest input) throws NotFoundException{
        return ResponseEntity.ok().body(challengeService.getChallengePageListByNickName(page, input.getNickname()));
    }

    @GetMapping("/list/search/tag/{page}")
    @ApiOperation(value="챌린지 리스트 태그로 검색 페이지", notes = "해당 태그 정확히 일치해야 검색")
    public ResponseEntity<List<ChallengeListResponse>> getChallengePageListByTag(@PathVariable("page") int page, @RequestBody TagRequest input) throws NotFoundException{
        return ResponseEntity.ok().body(challengeService.getChallengePageListByTag(page, input.getTag()));
    }

    @GetMapping("/{challengeId}")
    @ApiOperation(value="챌린지 상세 정보", notes = "챌린지 아이디를 넘겨주세요")
    public ResponseEntity<ChallengeListResponse> getChallenge(@PathVariable("challengeId") Long Id) throws NotFoundException{
        return ResponseEntity.ok().body(challengeService.getChallenge(Id));
    }

    @PostMapping("/chk-pw")
    @ApiOperation(value="챌린지 방 비밀번호 체크", notes = "챌린지 아이디를 넘겨주세요")
    public boolean pwCheck(@RequestBody passCheckRequest input) throws NotFoundException{
        return challengeService.checkPassword(input.getId(), input.getPassword());
    }
}
