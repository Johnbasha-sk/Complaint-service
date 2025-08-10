package com.municipal.user.controller;

import com.municipal.user.dto.LoginRequest;
import com.municipal.user.dto.RegisterRequest;
import com.municipal.user.model.Role;
import com.municipal.user.model.User;
import com.municipal.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("roles", Role.values());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterRequest request, Model model) {
        User user = userService.register(request);
        model.addAttribute("message", "Registered user: " + user.getUsername());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginRequest request, HttpServletResponse response) {
        return userService.authenticate(request)
                .map(auth -> {
                    Cookie cookie = new Cookie("jwt", auth.getToken());
                    cookie.setHttpOnly(true);
                    cookie.setPath("/");
                    cookie.setMaxAge(60 * 60 * 2);
                    response.addCookie(cookie);
                    String role = auth.getUser().getRole().name();
                    if ("CITIZEN".equals(role)) return "redirect:/citizen/home";
                    if ("STAFF".equals(role)) return "redirect:/staff/home";
                    return "redirect:/admin/home";
                })
                .orElse("auth/login");
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/auth/login";
    }
}