package com.a104.freeproject.Member.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SMSRequest {
    String type;
    String contentType;
    String countryCode;
    String from;
    String content;
    List<MessageRequest> messages;
}
