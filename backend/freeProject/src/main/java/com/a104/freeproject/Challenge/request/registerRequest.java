package com.a104.freeproject.Challenge.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class registerRequest {

    private String title;
    private String contents;
    private String imgurl;
    private boolean watch;
    private int roomtype;
    private String password;
    private int limitPeople;
    private String alarm;
    private int goal;
    private String unit;
    private long categoryId;
    private long detailCategoryId;
    private int alarmDir;
    @Setter
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private Timestamp startTime;
    @Setter
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private Timestamp endTime;
    private List<String> tagList;
    private int kkobak;
}