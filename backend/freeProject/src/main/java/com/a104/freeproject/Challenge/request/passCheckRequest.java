package com.a104.freeproject.Challenge.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class passCheckRequest {
    private Long id;
    private String password;
}
