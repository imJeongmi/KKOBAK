package com.a104.freeproject.Challenge.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppListResponse {

    private String imgurl;
    private boolean done;
    private long chlId;
    private long detailCategoryId;
    private int goal;
    private String title;
    private LocalDate endTime;
    private String contents;
    private boolean watch;
    private String unit;
    private int nowCnt;

}
