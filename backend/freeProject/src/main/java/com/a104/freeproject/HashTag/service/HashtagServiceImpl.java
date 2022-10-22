package com.a104.freeproject.HashTag.service;

import com.a104.freeproject.HashTag.entity.Hashtag;
import com.a104.freeproject.HashTag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-secret.properties")
})
public class HashtagServiceImpl implements HashtagService{

    private final HashtagRepository tagRepository;

    @Override
    public boolean registerTag(List<String> tagList) {

        for(String s : tagList){
            if(tagRepository.existsByName(s)) continue;
            tagRepository.save(Hashtag.builder().name(s).build());
        }

        return true;
    }
}
