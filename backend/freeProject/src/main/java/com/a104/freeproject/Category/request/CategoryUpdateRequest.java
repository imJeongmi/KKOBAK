package com.a104.freeproject.Category.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateRequest {
    private Long id;
    private String name;
    private String imgurl;
}
