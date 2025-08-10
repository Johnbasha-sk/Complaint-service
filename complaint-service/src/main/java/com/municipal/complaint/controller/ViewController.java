package com.municipal.complaint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/citizen-ui")
    public String citizen() {
        return "citizen";
    }

    @GetMapping("/staff-ui")
    public String staff() {
        return "staff";
    }

    @GetMapping("/admin-ui")
    public String admin() {
        return "admin";
    }
}