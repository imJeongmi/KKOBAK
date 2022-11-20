package com.a104.freeproject.Challenge.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedTotalStatResponse {
    private String year;
    private String month;
    private String day;
    private boolean done;
    private String timelen;
}
