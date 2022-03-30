package com.hanghae99.springweek01.controller;

import com.hanghae99.springweek01.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        } else {
            model.addAttribute("username", "");
        }
        return "index";
    }
    @GetMapping("/write")
    public String writePage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        model.addAttribute("username", userDetails.getUsername());
        return "write";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/detail/{id}")
    public String detailPage(Model model, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        model.addAttribute("id", id);
        model.addAttribute("username", userDetails.getUsername());
        return "detail";
    }

}