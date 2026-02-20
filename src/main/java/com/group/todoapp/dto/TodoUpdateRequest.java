package com.group.todoapp.dto;

public class TodoUpdateRequest {

    private String title;
    private String description;
    private Boolean isDone;

    public TodoUpdateRequest() {}

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Boolean getIsDone() { return isDone; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setIsDone(Boolean isDone) { this.isDone = isDone; }
}
