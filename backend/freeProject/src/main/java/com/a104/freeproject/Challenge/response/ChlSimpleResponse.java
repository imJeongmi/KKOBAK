package com.a104.freeproject.Challenge.response;

import com.a104.freeproject.HashTag.entity.Hashtag;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChlSimpleResponse {

    private Long ChlId;
    private String title;
    private String imgurl;
    private boolean isWatch;
    private int roomtype;
    private LocalDate startTime;
    private LocalDate endTime;
    List<String> tags;
}
