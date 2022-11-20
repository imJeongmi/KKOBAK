package com.a104.freeproject.Todolist.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodolistResponse {
    private Long todoId;
    private String contents;
    private boolean done;

}
