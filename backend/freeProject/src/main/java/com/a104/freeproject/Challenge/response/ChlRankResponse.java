package com.a104.freeproject.Challenge.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChlRankResponse {
    private int rank;
    private String nickname;
    private String imgurl;
    private double total_dist;
    private double avg_speed;
    private String time_len;
}
