package com.a104.freeproject.Challenge.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class registerRequest {

    private String title;
    private String contents;
    private String imgurl;
    private boolean isWatch;
    private int roomtype;
    private String password;
    private int limitPeople;
    private String alarm;
    private int status;
    private int goal;
    private String unit;
    private long categoryId;
    private long detailCategoryId;
    private List<String> tagList;
}
