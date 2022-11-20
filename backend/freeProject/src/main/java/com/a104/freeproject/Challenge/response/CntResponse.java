package com.a104.freeproject.Challenge.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CntResponse {
    private int goal;
    private int cnt;
    private boolean done;
}
