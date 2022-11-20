package com.a104.freeproject.HashTag.service;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.HashTag.entity.ChlTag;
import com.a104.freeproject.HashTag.entity.Hashtag;
import com.a104.freeproject.HashTag.repository.ChlTagRepository;
import com.a104.freeproject.HashTag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-secret.properties")
})
public class ChltagServiceImpl implements ChltagService{

    private final HashtagRepository tagRepository;
    private final ChlTagRepository chlTagRepository;

    @Override
    public void addChallengeTag(Challenge c, List<String> tagList) {
        for(String s : tagList){
            if(!tagRepository.existsByName(s)) tagRepository.save(Hashtag.builder().name(s).build());

            Hashtag tag = tagRepository.findByName(s);
            chlTagRepository.save(ChlTag.builder().challenge(c).hashtag(tag).build());
        }
    }
}
