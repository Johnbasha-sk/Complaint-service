package com.municipal.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "redirect:/auth/login";
    }

    @PreAuthorize("hasRole('CITIZEN')")
    @GetMapping("/citizen/home")
    public String citizenHome() {
        return "home/citizen";
    }

    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    @GetMapping("/staff/home")
    public String staffHome() {
        return "home/staff";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/home")
    public String adminHome() {
        return "home/admin";
    }
}