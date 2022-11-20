package com.a104.freeproject.Todolist.controller;

import com.a104.freeproject.Todolist.request.TodolistRequest;
import com.a104.freeproject.Todolist.response.TodolistResponse;
import com.a104.freeproject.Todolist.service.TodolistService;
import com.a104.freeproject.Todolist.service.TodolistServiceImpl;
import com.a104.freeproject.advice.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/todolist")
@RequiredArgsConstructor
@ApiOperation(value="[당일 ToDoList] Todolist Controller")
public class TodolistController {

    private final TodolistServiceImpl todolistService;

    // 개인 일자별 todolist 등록
    @PostMapping("/register")
    @ApiOperation(value = "[확인] 개인 일자별 todolist 등록, 정상 작동 시 return 해당 todolist id",
            notes = "date >> '2022-11-05' 형식으로 보내주세용")
    public ResponseEntity<Long> register(@RequestBody TodolistRequest input, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(todolistService.createTodo(input, req));
    }

    // 투두리스트 완료/취소
    @PostMapping("/change/status/{todoId}")
    @ApiOperation(value = "[확인] todolist 상태 변경(done -> true/false)", notes = "'/todolist/change/status/1' 형식으로 사용.\n"
            +"오늘것만 되게 해놨어요. 만약 다른 날짜도 하고 싶으면 말씀해주세요."
            +"정상 작동 시 return true (tory 확인 필수)")
    public ResponseEntity<Boolean> changeStatus(@PathVariable("todoId") Long todoId, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(todolistService.changeStatus(todoId, req));
    }

    // 일자별 투두리스트 조회
    @GetMapping("/{year}/{month}/{day}")
    @ApiOperation(value = "[확인] 일자별 todolist 조회",
            notes = "'todolist/2022/11/05' 형식으로 사용")
    public ResponseEntity<List<TodolistResponse>> gettodoList(@PathVariable("year") String year, @PathVariable("month") String month,
                                                              @PathVariable("day") String day, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(todolistService.gettodoList(year,month,day,req));
    }

    // 투두리스트 삭제
    @DeleteMapping("/remove/{todoId}")
    @ApiOperation(value = "todolist 삭제", notes = "'/todolist/remove/1' 형식으로 사용.\n"
            +"정상 작동 시 return true (tory 확인 필수)")
    public ResponseEntity<Boolean> removeTodo(@PathVariable("todoId") Long todoId, HttpServletRequest req) throws NotFoundException {
        return ResponseEntity.ok().body(todolistService.removeTodo(todoId, req));
    }

}
