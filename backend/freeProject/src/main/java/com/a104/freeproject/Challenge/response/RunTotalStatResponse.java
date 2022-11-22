package com.a104.freeproject.Challenge.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunTotalStatResponse {
    private String year;
    private String month;
    private String day;
    private boolean done;
    private double dist;
}
