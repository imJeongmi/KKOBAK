package com.a104.freeproject.Category.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailResponse {
    private Long detailId;
    private String detailName;
    private String imgurl;
}
