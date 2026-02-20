package com.group.todoapp.dto;

import com.group.todoapp.entity.User;
import java.io.Serializable;

// 세션에 저장할 최소한의 사용자 정보 (Entity 직접 저장 방지)
public class SessionUser implements Serializable {

    private Long id;
    private String email;
    private String nickname;

    public SessionUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
}
