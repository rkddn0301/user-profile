package com.example.user_profile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "사용자 프로필 시스템에 오신 것을 환영합니다!");
        return "home";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("title", "사용자 프로필");
        return "profile";
    }
} 