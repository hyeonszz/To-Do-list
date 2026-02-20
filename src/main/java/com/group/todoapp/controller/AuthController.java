package com.group.todoapp.controller;

import com.group.todoapp.dto.LoginRequest;
import com.group.todoapp.dto.SessionUser;
import com.group.todoapp.dto.SignupRequest;
import com.group.todoapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public static final String SESSION_KEY = "loginUser";

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ── 회원가입 ──────────────────────────────────────
    @GetMapping("/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupRequest request, Model model) {
        try {
            userService.signup(request);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/signup";
        }
    }

    // ── 로그인 ────────────────────────────────────────
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest request,
                        HttpSession session,
                        Model model) {
        try {
            SessionUser sessionUser = userService.login(request);
            session.setAttribute(SESSION_KEY, sessionUser);
            return "redirect:/todos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    // ── 로그아웃 ──────────────────────────────────────
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
