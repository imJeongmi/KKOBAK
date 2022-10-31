package com.a104.freeproject.HashTag.service;

import com.a104.freeproject.HashTag.entity.Hashtag;
import com.a104.freeproject.HashTag.repository.HashtagRepository;
import com.a104.freeproject.HashTag.response.TagsList;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-secret.properties")
})
public class HashtagServiceImpl implements HashtagService{

    private final HashtagRepository tagRepository;

    private  final String aiURL = "https://kkobak.ml/ai/image2";
    private  final String aiURL2 = "https://kkobak.ml/ai/sentence";
    @Override
    public boolean registerTag(List<String> tagList) {

        for(String s : tagList){
            if(tagRepository.existsByName(s)) continue;
            tagRepository.save(Hashtag.builder().name(s).build());
        }

        return true;
    }

    @Override
    public Map<String, List<String>> getTagsByImage(MultipartFile img) throws Exception{
        Map<String, List<String>> result = new HashMap<>();
        List<String> tagList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", img.getResource());

        HttpEntity<?> entity = new HttpEntity<>(body, header);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(aiURL).build();
        ResponseEntity<String> response = restTemplate.exchange(uri.toString(), HttpMethod.POST, entity, String.class);
        String responseString = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        TagsList tagsList = objectMapper.readValue(responseString, TagsList.class);

        for (String tag : tagsList.getTags()) {
            tagList.add(tag);
        }

        result.put("tags",tagList);
        return result;
    }

    @Override
    public Map<String, List<String>> getTagsByContents(String contents) throws Exception{
        Map<String, List<String>> result = new HashMap<>();
        List<String> tagList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("str", contents);

        HttpEntity<?> entity = new HttpEntity<>(body, header);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(aiURL2).build();
        ResponseEntity<String> response = restTemplate.exchange(uri.toString(), HttpMethod.POST, entity, String.class);
        String responseString = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        TagsList tagsList = objectMapper.readValue(responseString, TagsList.class);

        for (String tag : tagsList.getTags()) {
            tagList.add(tag);
        }

        result.put("tags",tagList);
        return result;

    }
}
