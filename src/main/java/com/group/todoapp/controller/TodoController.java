package com.group.todoapp.controller;

import com.group.todoapp.dto.SessionUser;
import com.group.todoapp.dto.TodoCreateRequest;
import com.group.todoapp.dto.TodoResponse;
import com.group.todoapp.dto.TodoUpdateRequest;
import com.group.todoapp.service.TodoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    private Long getLoginUserId(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(AuthController.SESSION_KEY);
        if (sessionUser == null) throw new IllegalStateException("로그인이 필요합니다.");
        return sessionUser.getId();
    }

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoCreateRequest request,
                                               HttpSession session) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(todoService.create(request, getLoginUserId(session)));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> findAll(HttpSession session) {
        return ResponseEntity.ok(todoService.findAll(getLoginUserId(session)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> findById(@PathVariable Long id, HttpSession session) {
        return ResponseEntity.ok(todoService.findById(id, getLoginUserId(session)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id,
                                               @RequestBody TodoUpdateRequest request,
                                               HttpSession session) {
        return ResponseEntity.ok(todoService.update(id, request, getLoginUserId(session)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpSession session) {
        todoService.delete(id, getLoginUserId(session));
        return ResponseEntity.noContent().build();
    }
}
