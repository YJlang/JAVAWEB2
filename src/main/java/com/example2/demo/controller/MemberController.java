package com.example2.demo.controller;

// 필요한 라이브러리들을 임포트
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

@Controller // 스프링 MVC 컨트롤러임을 나타내는 어노테이션
public class MemberController {
    private final MemberService memberService; // 회원 서비스 객체 선언

    // 생성자를 통한 의존성 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입 페이지로 이동하는 GET 요청 처리
    @GetMapping("/join_new")
    public String join_new() {
        return "join_new"; // join_new.html 페이지 반환
    }

    // 회원가입 데이터를 처리하는 POST 요청 처리
    @PostMapping("/api/members")
    public String addmembers(@ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request); // 회원 정보 저장
        return "join_end"; // 가입 완료 페이지로 이동
    }

    // 회원가입 완료 페이지로 이동하는 GET 요청 처리
    @GetMapping("/join_end")
    public String join_end() {
        return "join_end";
    }

    // 로그인 페이지로 이동하는 GET 요청 처리
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 로그아웃 처리하는 GET 요청 처리
    @GetMapping("/api/logout")
    public String member_logout(Model model, HttpServletRequest request2, HttpServletResponse response) {
        try {
            // 현재 사용자의 세션만 가져오기
            HttpSession session = request2.getSession(false);
            if (session != null) {
                // 현재 세션의 email 값 확인
                String email = (String) session.getAttribute("email");
                
                // 현재 세션만 무효화
                session.invalidate();
                
                // 현재 사용자의 쿠키만 삭제
                Cookie[] cookies = request2.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("JSESSIONID")) {
                            cookie.setValue("");
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }
            }
            return "login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // 로그인 처리하는 POST 요청 처리
    @PostMapping("/api/login_check")
    public String checkMembers(@ModelAttribute AddMemberRequest request, Model model,
                                HttpServletRequest request2, HttpServletResponse response) {
        try {
            // 로그인 처리
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());
            
            // 새로운 세션 생성 (기존 세션 유지)
            HttpSession session = request2.getSession(true);
            
            // 사용자별 고유 세션 ID 생성
            String sessionId = UUID.randomUUID().toString() + "_" + request.getEmail();
            String email = request.getEmail();
            
            // 세션에 사용자 정보 저장
            session.setAttribute("userId", sessionId);
            session.setAttribute("email", email);

            model.addAttribute("member", member);
            return "redirect:/board_list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }       

}
