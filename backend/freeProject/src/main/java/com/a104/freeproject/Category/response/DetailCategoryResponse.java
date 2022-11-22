package com.a104.freeproject.Category.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailCategoryResponse {

    private Long detailId;
    private Long categoryId;
    private String detailName;
    private String imgurl;

}
