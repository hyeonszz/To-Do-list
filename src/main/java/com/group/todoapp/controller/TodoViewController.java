package com.group.todoapp.controller;

import com.group.todoapp.dto.SessionUser;
import com.group.todoapp.dto.TodoCreateRequest;
import com.group.todoapp.dto.TodoUpdateRequest;
import com.group.todoapp.service.TodoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoViewController {

    private final TodoService todoService;

    public TodoViewController(TodoService todoService) {
        this.todoService = todoService;
    }

    // 세션에서 로그인 유저 꺼내기 (없으면 null)
    private SessionUser getLoginUser(HttpSession session) {
        return (SessionUser) session.getAttribute(AuthController.SESSION_KEY);
    }

    // 목록 페이지
    @GetMapping
    public String list(HttpSession session, Model model) {
        SessionUser loginUser = getLoginUser(session);
        if (loginUser == null) return "redirect:/login";

        model.addAttribute("todos", todoService.findAll(loginUser.getId()));
        model.addAttribute("nickname", loginUser.getNickname());
        return "todo/list";
    }

    // 등록 폼 페이지
    @GetMapping("/new")
    public String createForm(HttpSession session) {
        if (getLoginUser(session) == null) return "redirect:/login";
        return "todo/create";
    }

    // 등록 처리
    @PostMapping
    public String create(@ModelAttribute TodoCreateRequest request, HttpSession session) {
        SessionUser loginUser = getLoginUser(session);
        if (loginUser == null) return "redirect:/login";

        todoService.create(request, loginUser.getId());
        return "redirect:/todos";
    }

    // 완료 여부 토글 (체크박스)
    @PostMapping("/{id}/done")
    public String toggleDone(@PathVariable Long id,
                             @RequestParam(defaultValue = "false") boolean isDone,
                             HttpSession session) {
        SessionUser loginUser = getLoginUser(session);
        if (loginUser == null) return "redirect:/login";

        TodoUpdateRequest request = new TodoUpdateRequest();
        request.setIsDone(isDone);
        todoService.update(id, request, loginUser.getId());
        return "redirect:/todos";
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        SessionUser loginUser = getLoginUser(session);
        if (loginUser == null) return "redirect:/login";

        todoService.delete(id, loginUser.getId());
        return "redirect:/todos";
    }
}
