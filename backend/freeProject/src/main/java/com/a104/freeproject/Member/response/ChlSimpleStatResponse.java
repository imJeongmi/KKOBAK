package com.a104.freeproject.Member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChlSimpleStatResponse {
    // 참여한 챌린지 갯수, 진행중인 챌린지 갯수, 완료한 챌린지 갯수, 성공한 챌린지 갯수, 실패한 챌린지 갯수 API
    private int totalChl;
    private int ingChl;
    private int finChl;
    private int sucChl;
    private int failChl;
}
