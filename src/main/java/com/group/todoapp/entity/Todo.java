package com.group.todoapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private boolean isDone = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Todo() {}

    public Todo(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.isDone = false;
        this.user = user;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isDone() { return isDone; }
    public User getUser() { return user; }

    public void update(String title, String description, Boolean isDone) {
        if (title != null) this.title = title;
        if (description != null) this.description = description;
        if (isDone != null) this.isDone = isDone;
    }
}
