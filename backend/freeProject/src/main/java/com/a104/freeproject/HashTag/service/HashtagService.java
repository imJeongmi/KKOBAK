package com.a104.freeproject.HashTag.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface HashtagService {
    boolean registerTag(List<String> tagList);

    Map<String, List<String>> getTagsByImage(MultipartFile multipartFile) throws Exception;

    Map<String, List<String>> getTagsByContents(String contents) throws Exception;
}
