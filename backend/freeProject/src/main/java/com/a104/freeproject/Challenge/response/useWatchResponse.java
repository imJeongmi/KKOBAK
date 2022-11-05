package com.a104.freeproject.Challenge.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class useWatchResponse {
    private Long id;
    private Long categoryId;
    private Long detailCategoryId;
    private Long writer;
    private String title;
    private String contents;
    private String imgurl;
    private int roomtype;
    private String password;
    private int limitPeolple;
    private int currentNum;
    private String alarm;
    private int goal;
    private String unit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp endTime;
    private List<String> tagList;
}
