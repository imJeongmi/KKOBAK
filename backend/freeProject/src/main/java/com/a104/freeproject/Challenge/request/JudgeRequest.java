package com.a104.freeproject.Challenge.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeRequest {
    private Long cid;
    private LocalDateTime startTime;
    private String lat;
    private String lng;
}
