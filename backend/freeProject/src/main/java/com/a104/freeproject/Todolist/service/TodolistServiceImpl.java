package com.a104.freeproject.Todolist.service;

import com.a104.freeproject.Member.entity.Member;
import com.a104.freeproject.Member.service.MemberServiceImpl;
import com.a104.freeproject.Todolist.entity.Todolist;
import com.a104.freeproject.Todolist.repository.TodolistRepository;
import com.a104.freeproject.Todolist.request.TodolistRequest;
import com.a104.freeproject.Todolist.response.TodolistResponse;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TodolistServiceImpl implements TodolistService{

    private final MemberServiceImpl memberService;
    private final TodolistRepository todolistRepository;

    @Override
    public long createTodo(TodolistRequest input, HttpServletRequest req) throws NotFoundException {

        Member member = memberService.findEmailbyToken(req);

        if(todolistRepository.existsByContentsAndDate(input.getContents(), input.getDate()))
            throw new NotFoundException("이미 등록한 todolist 입니다.");

        Long id = todolistRepository.save(Todolist.builder().member(member)
                .date(input.getDate()).contents(input.getContents())
                .build()).getId();

        return id;
    }

    @Override
    public boolean changeStatus(Long todoId, HttpServletRequest req) throws NotFoundException {
        Member member = memberService.findEmailbyToken(req);

        if(!todolistRepository.existsById(todoId)) throw new NotFoundException("존재하지 않는 todolist 입니다.");
        Todolist todo = todolistRepository.findById(todoId).get();
        if(todo.isDone()) todo.setCompleteTime(null);
        else todo.setCompleteTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        todo.setDone(!todo.isDone());
        todolistRepository.save(todo);

        return true;
    }

    @Override
    public List<TodolistResponse> gettodoList(String year, String month, String day, HttpServletRequest req) throws NotFoundException {
        Member member = memberService.findEmailbyToken(req);
        LocalDate date = LocalDate.parse(year+"-"+month+"-"+day);
        List<Todolist> list = todolistRepository.findAllByMemberAndDate(member,date);

        List<TodolistResponse> output = new LinkedList<>();
        for(Todolist todo : list){
            output.add(TodolistResponse.builder().todoId(todo.getId())
                    .contents(todo.getContents()).done(todo.isDone()).build());
        }

        return output;
    }

    @Override
    public boolean removeTodo(Long todoId, HttpServletRequest req) throws NotFoundException {
        Member member = memberService.findEmailbyToken(req);
        if(!todolistRepository.existsById(todoId)) throw new NotFoundException("존재하지 않는 todolist 입니다.");
        Todolist todo = todolistRepository.findById(todoId).get();
        todolistRepository.delete(todo);
        return true;
    }
}
