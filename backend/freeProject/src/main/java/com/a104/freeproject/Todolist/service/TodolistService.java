package com.a104.freeproject.Todolist.service;

import com.a104.freeproject.Todolist.request.TodolistRequest;
import com.a104.freeproject.Todolist.response.TodolistResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TodolistService {

    long createTodo(TodolistRequest input, HttpServletRequest req) throws NotFoundException;
    boolean changeStatus(Long todoId, HttpServletRequest req) throws NotFoundException;
    List<TodolistResponse> gettodoList(String year, String month, String day, HttpServletRequest req) throws NotFoundException;
    boolean removeTodo(Long todoId, HttpServletRequest req) throws NotFoundException;
}
