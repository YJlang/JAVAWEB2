package com.example2.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller // 컨트롤러 어노테이션 명시

public class DemoController{
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("data", " 방갑습니다."); //model설정
        return "hello"; //hello.html 연결
    }
    
}