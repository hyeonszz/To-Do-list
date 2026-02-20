package com.group.todoapp.service;

import com.group.todoapp.dto.TodoCreateRequest;
import com.group.todoapp.dto.TodoResponse;
import com.group.todoapp.dto.TodoUpdateRequest;
import com.group.todoapp.entity.Todo;
import com.group.todoapp.entity.User;
import com.group.todoapp.repository.TodoRepository;
import com.group.todoapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TodoResponse create(TodoCreateRequest request, Long userId) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("title은 필수입니다.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Todo todo = new Todo(request.getTitle(), request.getDescription(), user);
        return new TodoResponse(todoRepository.save(todo));
    }

    public List<TodoResponse> findAll(Long userId) {
        return todoRepository.findAllByUserId(userId).stream()
                .map(TodoResponse::new)
                .collect(Collectors.toList());
    }

    public TodoResponse findById(Long id, Long userId) {
        Todo todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Todo가 존재하지 않습니다. id=" + id));
        return new TodoResponse(todo);
    }

    @Transactional
    public TodoResponse update(Long id, TodoUpdateRequest request, Long userId) {
        Todo todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Todo가 존재하지 않습니다. id=" + id));
        todo.update(request.getTitle(), request.getDescription(), request.getIsDone());
        return new TodoResponse(todo);
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Todo todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Todo가 존재하지 않습니다. id=" + id));
        todoRepository.delete(todo);
    }
}
