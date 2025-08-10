package com.municipality.userservice.controller;

import com.municipality.userservice.dto.LoginRequest;
import com.municipality.userservice.dto.RegisterRequest;
import com.municipality.userservice.entity.Role;
import com.municipality.userservice.entity.User;
import com.municipality.userservice.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginRequest loginRequest,
                       BindingResult bindingResult,
                       HttpServletResponse response,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        try {
            String token = userService.authenticate(loginRequest);
            User user = userService.findByUsername(loginRequest.getUsername()).orElse(null);
            
            if (user != null) {
                // Set JWT token in HTTP-only cookie
                Cookie cookie = new Cookie("authToken", token);
                cookie.setHttpOnly(true);
                cookie.setSecure(false); // Set to true in production with HTTPS
                cookie.setPath("/");
                cookie.setMaxAge(24 * 60 * 60); // 24 hours
                response.addCookie(cookie);

                // Redirect based on role
                String redirectUrl = userService.getRedirectUrlByRole(user.getRole());
                return "redirect:" + redirectUrl;
            } else {
                model.addAttribute("error", "Authentication failed");
                return "auth/login";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        model.addAttribute("roles", Role.values());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterRequest registerRequest,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "auth/register";
        }

        try {
            userService.register(registerRequest);
            redirectAttributes.addFlashAttribute("success", 
                "Registration successful! Please login with your credentials.");
            return "redirect:/auth/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", Role.values());
            return "auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        // Clear JWT cookie
        Cookie cookie = new Cookie("authToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        redirectAttributes.addFlashAttribute("success", "You have been logged out successfully.");
        return "redirect:/auth/login";
    }
}