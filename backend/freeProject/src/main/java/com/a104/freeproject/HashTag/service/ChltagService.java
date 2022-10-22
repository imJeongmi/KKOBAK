package com.a104.freeproject.HashTag.service;

import com.a104.freeproject.Challenge.entity.Challenge;
import com.a104.freeproject.HashTag.entity.Hashtag;

import java.util.List;

public interface ChltagService {

    void addChallengeTag(Challenge c, List<String> tagList);

}
