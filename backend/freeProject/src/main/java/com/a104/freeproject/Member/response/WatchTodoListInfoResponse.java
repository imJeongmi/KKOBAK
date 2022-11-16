package com.a104.freeproject.Member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchTodoListInfoResponse {
    // 챌린지 아이디, 타이틀, 실행 여부
    private Long chlId;
    private String title;
    private String contents;
    private Long categoryId;
    private Long detailCategoryId;
    private int goal;
    private String unit;
    private boolean isDone;
    private int kkobak;
    private int cnt;


}
