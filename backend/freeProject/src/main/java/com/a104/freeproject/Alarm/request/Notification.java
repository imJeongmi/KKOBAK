package com.a104.freeproject.Alarm.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Notification {
    private String title;
    private String body;
    private String message;
}

