package com.a104.freeproject.Member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SMSResponse {
    String requestId;
    LocalDateTime requestTime;
    String statusCode;
    String statusName;
}
