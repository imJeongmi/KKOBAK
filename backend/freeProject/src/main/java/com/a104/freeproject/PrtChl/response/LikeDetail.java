package com.a104.freeproject.PrtChl.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDetail {
    private Long chlId;
    private Long detailCategoryId;
    private String title;
    private String imgurl;
    private boolean watch;
    private int roomtype;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endTime;
    List<String> tags;
}


/*
[
  {
    "chlId": 0,
    "endTime": "string",
    "imgurl": "string",
    "roomtype": 0,
    "startTime": "string",
    "tags": [
      "string"
    ],
    "title": "string",
    "watch": true
  }
]
 */