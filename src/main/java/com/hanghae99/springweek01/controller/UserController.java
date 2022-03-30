package com.hanghae99.springweek01.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae99.springweek01.dto.KakaoUserInfoDto;
import com.hanghae99.springweek01.dto.SignupRequestDto;
import com.hanghae99.springweek01.service.KakaoUserService;
import com.hanghae99.springweek01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;



    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService) {
        this.userService = userService;
        this.kakaoUserService = kakaoUserService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto, Model model) {
        try {
            userService.registerUser(requestDto);
        }catch (IllegalArgumentException e){
            model.addAttribute("msg", e.getMessage());
            return "signup";
        }
        return "redirect:/user/login";
    }
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }
}