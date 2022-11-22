package com.a104.freeproject.Alarm.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private String to;
    private String priority;
    private Notification notification;

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}

