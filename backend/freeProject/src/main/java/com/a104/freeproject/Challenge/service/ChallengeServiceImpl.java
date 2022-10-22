package com.a104.freeproject.Challenge.service;

import com.a104.freeproject.Category.entity.Category;
import com.a104.freeproject.Category.entity.DetailCategory;
import com.a104.freeproject.Category.repository.CategoryRepository;
import com.a104.freeproject.Category.repository.DetailCategoryRepository;
import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.Challenge.repository.ChallengeRepository;
import com.a104.freeproject.Challenge.request.registerRequest;
import com.a104.freeproject.HashTag.entity.ChlTag;
import com.a104.freeproject.HashTag.entity.Hashtag;
import com.a104.freeproject.HashTag.repository.ChlTagRepository;
import com.a104.freeproject.HashTag.repository.HashtagRepository;
import com.a104.freeproject.HashTag.service.ChltagServiceImpl;
import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Hash;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService{

    private final ChallengeRepository challengeRepository;
    private final CategoryRepository categoryRepository;
    private final DetailCategoryRepository detailRepository;
    private final ChltagServiceImpl chltagService;
    private final MemberServiceImpl memberService;

    @Override
    public boolean register(registerRequest input, HttpServletRequest req) throws NotFoundException {

        Member member;

        try{
            member = memberService.findEmailbyToken(req);
        } catch (Exception e){
            throw e;
        }

        if(!categoryRepository.existsById(input.getCategoryId())) throw new NotFoundException("카테고리를 다시 입력해주세요.");
        Category category = categoryRepository.findById(input.getCategoryId()).get();
        Challenge challenge;

        if(category.getName().equals("기타")){ // 세부 카테고리가 없음
            challenge = Challenge.builder().title(input.getTitle()).contents(input.getContents()).imgurl(input.getImgurl()).
                    isWatch(input.isWatch()).roomtype(input.getRoomtype()).password(input.getPassword()).writer(member.getId())
                    .limitPeople(input.getLimitPeople()).alarm(input.getAlarm())
                    .status(input.getStatus()).goal(input.getGoal()).unit(input.getUnit()).category(category)
                    .build();
        }
        else{ // 세부 카테고리가 있음
            if(!detailRepository.existsByCategoryAndId(category,input.getDetailCategoryId())) throw new NotFoundException("세부 카테고리를 다시 입력해주세요.");
            DetailCategory detail = detailRepository.findById(input.getDetailCategoryId()).get();

            challenge = Challenge.builder().title(input.getTitle()).contents(input.getContents()).imgurl(input.getImgurl()).
                    isWatch(input.isWatch()).roomtype(input.getRoomtype()).password(input.getPassword()).writer(member.getId())
                    .limitPeople(input.getLimitPeople()).alarm(input.getAlarm())
                    .status(input.getStatus()).goal(input.getGoal()).unit(input.getUnit()).category(category).detailCategory(detail)
                    .build();
        }

        Long cid = challengeRepository.save(challenge).getId();
        Challenge c = challengeRepository.findById(cid).get();

        //해시태그 추가
        chltagService.addChallengeTag(c,input.getTagList());

        return true;
    }

    @Override
    public List<ChlTag> test(Long cNum) {
        try{
            Challenge c = challengeRepository.findById(cNum).get();
            return c.getTagList();
        } catch(Exception e){
            throw e;
        }
    }
}
