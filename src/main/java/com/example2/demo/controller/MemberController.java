package com.example2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.UUID;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example2.demo.model.service.AddMemberRequest;
import com.example2.demo.model.service.MemberService;
import com.example2.demo.model.domain.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/join_new") // 회원 가입 페이지 연결
    public String join_new() {
        return "join_new"; // .HTML 연결
    }

    @PostMapping("/api/members") // 회원 가입 저장
    public String addmembers(@ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request);
        return "join_end"; // .HTML 연결
    }

    @GetMapping("/join_end") // 회원 가입 완료 페이지 연결
    public String join_end() {
        return "join_end"; // .HTML 연결
    }

    @GetMapping("/login") // 로그인 페이지 연결
    public String login() {
        return "login"; // .HTML 연결
    }

   @GetMapping("/api/logout") // 로그아웃 버튼 동작
    public String member_logout(Model model, HttpServletRequest request2, HttpServletResponse response) {
        try {
            HttpSession session = request2.getSession(false); // 기존 세션 가져오기(존재하지 않으면 null 반환)
            session.invalidate(); // 기존 세션 무효화
            Cookie cookie = new Cookie("JSESSIONID", null); // JSESSIONID is the default session cookie name
            cookie.setPath("/"); // Set the path for the cookie
            cookie.setMaxAge(0); // Set cookie expiration to 0 (removes the cookie)
            response.addCookie(cookie); // Add cookie to the response
            session = request2.getSession(true); // 새로운 세션 생성
            System.out.println("세션 userId: " + session.getAttribute("userId" )); // 초기화 후 IDE 터미널에 세션 값 출력
            return "login"; // 로그인 페이지로 리다이렉트
        } catch (IllegalArgumentException e) {  
            model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
            return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
        }
    }

    @PostMapping("/api/login_check") // 로그인(아이디, 패스워드) 체크
    public String checkMembers(@ModelAttribute AddMemberRequest request, Model model,
                                HttpServletRequest request2, HttpServletResponse response) {
        try {
            HttpSession session = request2.getSession(false); // 기존 세션 가져오기(존재하지 않으면 null 반환)
            if(session != null) {
                session.invalidate(); // 기존 세션 무효화
                Cookie cookie = new Cookie("JSESSIONID", null); // JSESSIONID is the default session cookie name
                cookie.setPath("/"); // Set the path for the cookie
                cookie.setMaxAge(0); // Set cookie expiration to 0 (removes the cookie)
                response.addCookie(cookie); // Add cookie to the response
            }
            session = request2.getSession(true); // 새로운 세션 생성
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword()); // 패스워드 반환
            String sessionId = UUID.randomUUID().toString(); // 임의로 고유아이디 생성
            String email = request.getEmail();
            
            session.setAttribute("userId", sessionId); // 세션에 아이디 저장
            session.setAttribute("email", email); // 세션에 이메일 저장

            model.addAttribute("member", member); // 로그인 성공 시 회원 정보 전달
            return "redirect:/board_list"; // 로그인 성공 후 이동할 페이지
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
            return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
        }
    }       



}
