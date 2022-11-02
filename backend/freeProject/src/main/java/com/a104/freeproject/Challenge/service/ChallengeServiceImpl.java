package com.a104.freeproject.Challenge.service;

import com.a104.freeproject.Category.entity.Category;
import com.a104.freeproject.Category.entity.DetailCategory;
import com.a104.freeproject.Category.repository.CategoryRepository;
import com.a104.freeproject.Category.repository.DetailCategoryRepository;
import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.repository.ChallengeRepository;
import com.a104.freeproject.Challenge.request.registerRequest;
import com.a104.freeproject.Challenge.response.ChallengeListResponse;
import com.a104.freeproject.Challenge.response.ChlUserNameResponse;
import com.a104.freeproject.Challenge.response.ChlUserSimpleStatResponse;
import com.a104.freeproject.HashTag.entity.ChlTag;
import com.a104.freeproject.HashTag.repository.HashtagRepository;
import com.a104.freeproject.HashTag.service.ChltagServiceImpl;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.repository.MemberRepository;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.PrtChl.entity.PrtChl;
import com.a104.freeproject.PrtChl.service.PrtChlServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService{

    private final ChallengeRepository challengeRepository;
    private final CategoryRepository categoryRepository;
    private final DetailCategoryRepository detailRepository;
    private  final MemberRepository memberRepository;
    private final HashtagRepository hashtagRepository;
    private final ChltagServiceImpl chltagService;
    private final MemberServiceImpl memberService;
    private final ChlTimeServiceImpl chlTimeService;
    private final PrtChlServiceImpl prtChlService;

    @Override
    public boolean register(registerRequest input, HttpServletRequest req) throws NotFoundException {

        System.out.println("처음 들어온 값 service >> "+input.getStartTime());
        Member member;

        try{
            member = memberService.findEmailbyToken(req);
        } catch (Exception e){
            throw e;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(tz);
        input.setStartTime(Timestamp.valueOf(sdf.format(input.getStartTime())));
        input.setEndTime(Timestamp.valueOf(sdf.format(input.getEndTime())));

        if(!categoryRepository.existsById(input.getCategoryId())) throw new NotFoundException("카테고리를 다시 입력해주세요.");
        Category category = categoryRepository.findById(input.getCategoryId()).get();
        Challenge challenge;

        if(category.getName().equals("기타")){ // 세부 카테고리가 없음
            challenge = Challenge.builder().title(input.getTitle()).contents(input.getContents()).imgurl(input.getImgurl()).
                    isWatch(input.isWatch()).roomtype(input.getRoomtype()).password(input.getPassword()).writer(member.getId())
                    .limitPeople(input.getLimitPeople()).alarm(input.getAlarm())
                    .goal(input.getGoal()).unit(input.getUnit()).category(category)
                    .build();
        }
        else{ // 세부 카테고리가 있음
            if(!detailRepository.existsByCategoryAndId(category,input.getDetailCategoryId())) throw new NotFoundException("세부 카테고리를 다시 입력해주세요.");
            DetailCategory detail = detailRepository.findById(input.getDetailCategoryId()).get();

            challenge = Challenge.builder().title(input.getTitle()).contents(input.getContents()).imgurl(input.getImgurl()).
                    isWatch(input.isWatch()).roomtype(input.getRoomtype()).password(input.getPassword()).writer(member.getId())
                    .limitPeople(input.getLimitPeople()).alarm(input.getAlarm())
                    .goal(input.getGoal()).unit(input.getUnit()).category(category).detailCategory(detail)
                    .build();
        }

        // 시간 체크하고 지금시간보다 전이면(입력하느라 시간 지난거 등을 생각해서)
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Timestamp now = Timestamp.valueOf(sdf.format(time));

        if(input.getStartTime().equals(now) || input.getStartTime().before(now)) challenge.setStatus(1);

        Long cid = challengeRepository.save(challenge).getId();
        Challenge c = challengeRepository.findById(cid).get();

        //해시태그 추가
        chltagService.addChallengeTag(c,input.getTagList());

        //ChlTime 추가
        chlTimeService.addRow(c,input.getStartTime(), input.getEndTime());

        //PrtChl 추가
        prtChlService.participate(c.getId(),req, input.getAlarmDir());

        return true;
    }

    @Override
    public List<ChlUserNameResponse> getUserInfo(Long chlId) throws NotFoundException {
        if(!challengeRepository.existsById(chlId)) throw new NotFoundException("챌린지가 존재하지 않습니다.");
        Challenge c = challengeRepository.findById(chlId).get();

        List<PrtChl> chlList = c.getChlList();
        List<ChlUserNameResponse> output = new LinkedList<>();

        for (PrtChl p:chlList){
            output.add(ChlUserNameResponse.builder().nickname(p.getMember().getNickname()).build());
        }

        return output;
    }

    @Override
    public List<ChlUserSimpleStatResponse> getUserSimpleStatInfo(Long chlId) throws NotFoundException {
        if(!challengeRepository.existsById(chlId)) throw new NotFoundException("챌린지가 존재하지 않습니다.");
        Challenge c = challengeRepository.findById(chlId).get();

        List<PrtChl> chlList = c.getChlList();
        List<ChlUserSimpleStatResponse> output = new LinkedList<>();

        for (PrtChl p:chlList){
            double ratio = (1.0*p.getSucDay())/(p.getSucDay()+p.getFailDay());
            output.add(ChlUserSimpleStatResponse.builder().nickname(p.getMember().getNickname())
                    .sucRatio(Math.round(ratio*1000)/1000.0).build());
        }

        return output;
    }

    @Override
    public List<ChallengeListResponse> getChallengePageList(int page) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        Page<Challenge> challengePage = challengeRepository.findAllChallenge(pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }
    @Override
    public List<ChallengeListResponse> getChallengePageListByCategory(int page, Long id) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        Page<Challenge> challengePage = challengeRepository.findAllByCategoryId(id, pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }


    @Override
    public List<ChallengeListResponse> getChallengePageListByDetailCategory(int page, Long id) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        Page<Challenge> challengePage = challengeRepository.findAllByDetailCategoryId(id, pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }
    @Override
    public List<ChallengeListResponse> getChallengePageListByTitle(int page, String word) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        Page<Challenge> challengePage = challengeRepository.findByTitleContains(word, pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }

    @Override
    public List<ChallengeListResponse> getChallengePageListByNickName(int page, String nickName) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        Long writerId = memberRepository.findByNickname(nickName).getId();
        Page<Challenge> challengePage = challengeRepository.findByWriter(writerId, pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }

    @Override
    public List<ChallengeListResponse> getChallengePageListByTag(int page, String tag) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.Direction.DESC, "id");
        List<ChlTag> chlTags= hashtagRepository.findByName(tag).getChlTags();
        //페이지네이션용
        List<Long> challengeIdList = new ArrayList<>();
        for (ChlTag chlTag : chlTags) {
            challengeIdList.add(chlTag.getChallenge().getId());
        }
        Page<Challenge> challengePage = challengeRepository.findByIdIn(challengeIdList, pageRequest);
        List<Challenge> content = challengePage.getContent();

        return makeResponse(content);
    }

    @Override
    public ChallengeListResponse getChallenge(Long id) throws NotFoundException{
        Challenge c = challengeRepository.findById(id).get();
        List<ChlTag> tagList= c.getTagList();
        List<String> tagListName = new ArrayList<>();
        for (ChlTag a: tagList){
            tagListName.add(a.getHashtag().getName());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

        ChallengeListResponse result = ChallengeListResponse.builder()
                .id(c.getId())
                .categoryId(c.getCategory().getId())
                .detailCategoryId(c.getDetailCategory().getId())
                .writer(c.getWriter())
                .title(c.getTitle())
                .contents(c.getContents())
                .imgurl(c.getImgurl())
                .isWatch(c.isWatch())
                .roomtype(c.getRoomtype())
                .password(c.getPassword())
                .limitPeolple(c.getLimitPeople())
                .currentNum(c.getCurrentNum())
                .alarm(c.getAlarm())
                .goal(c.getGoal())
                .unit(c.getUnit())
                .isFin(c.isFin())
                .nickName(memberRepository.findById(c.getWriter()).get().getNickname())
                .startTime(c.getChlTime().getStartTime())
                .endTime(c.getChlTime().getEndTime())
                .tagList(tagListName)
                .build();
        System.out.println("time zone : "+result.getStartTime());

        return result;
    }

    @Override
    public boolean checkPassword(Long id, String password) throws  NotFoundException{
        if(!challengeRepository.existsById(id)) throw new NotFoundException("해당 방이 없습니다.");
        Challenge challenge = challengeRepository.findById(id).get();
        if(password.equals(challenge.getPassword())){
            return true;
        }
        return false;
     }

    @Override
    public int getChallengePageCnt(Pageable pageable) {
        Page<Challenge> page = challengeRepository.findAllChallengeSet(pageable);
        return page.getTotalPages();
    }


    public List<ChallengeListResponse> makeResponse(List<Challenge> content) {
        List<ChallengeListResponse> result = new ArrayList<>();

        for (Challenge c : content) {
            List<ChlTag> tagList= c.getTagList();
            List<String> tagListName = new ArrayList<>();
            for (ChlTag a: tagList){
                tagListName.add(a.getHashtag().getName());
            }
            ChallengeListResponse temp = ChallengeListResponse.builder()
                    .id(c.getId())
                    .categoryId(c.getCategory().getId())
                    .detailCategoryId(c.getDetailCategory().getId())
                    .writer(c.getWriter())
                    .title(c.getTitle())
                    .contents(c.getContents())
                    .imgurl(c.getImgurl())
                    .isWatch(c.isWatch())
                    .roomtype(c.getRoomtype())
                    .password(c.getPassword())
                    .limitPeolple(c.getLimitPeople())
                    .currentNum(c.getCurrentNum())
                    .alarm(c.getAlarm())
                    .goal(c.getGoal())
                    .unit(c.getUnit())
                    .isFin(c.isFin())
                    .nickName(memberRepository.findById(c.getWriter()).get().getNickname())
                    .startTime(c.getChlTime().getStartTime())
                    .endTime(c.getChlTime().getEndTime())
                    .tagList(tagListName)
                    .build();
            System.out.println(c.getTitle());
            result.add(temp);
        }

        return result;
    }


}


