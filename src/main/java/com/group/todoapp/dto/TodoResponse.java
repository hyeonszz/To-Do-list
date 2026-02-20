package com.group.todoapp.dto;

import com.group.todoapp.entity.Todo;

public class TodoResponse {

    private Long id;
    private String title;
    private String description;
    private boolean isDone;

    public TodoResponse(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.description = todo.getDescription();
        this.isDone = todo.isDone();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isDone() { return isDone; }
}
