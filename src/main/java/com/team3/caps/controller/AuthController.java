package com.team3.caps.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.team3.caps.security.SecurityUtil;

@Controller
public class AuthController {

    @GetMapping({ "/" })
    public String home() {
        return ("redirect:/login");
    }

    @GetMapping({ "/login" })
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = SecurityUtil.getfirstAuthority(authentication);

        switch (role) {
            case "ROLE_Admin":
                return "redirect:/admin";
            case "ROLE_Lecturer":
                return "redirect:/lecturer";
            case "ROLE_Student":
                return "redirect:/student/view-all-courses";
            default:
                return "home";
        }
    }
}
