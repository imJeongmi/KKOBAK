package com.a104.freeproject.Challenge.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChlUserSimpleStatResponse {
    private String nickname;
    private String imgurl;
    private double sucRatio;
    private int sucCnt;
    private int failCnt;
}
