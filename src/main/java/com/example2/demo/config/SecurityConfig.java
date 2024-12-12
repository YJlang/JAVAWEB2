// 스프링 시큐리티 설정을 위한 패키지 선언
package com.example2.demo.config;

// 필요한 스프링 시큐리티 관련 클래스들을 임포트
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

// 스프링의 설정 클래스임을 나타내는 어노테이션
@Configuration 
// 웹 보안 기능을 활성화하는 어노테이션
@EnableWebSecurity 
// 스프링 시큐리티의 주요 설정을 담당하는 클래스
public class SecurityConfig { 

    // SecurityFilterChain을 빈으로 등록하여 보안 설정을 구성
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // HTTP 헤더 설정
            .headers(headers -> headers
                // XSS(크로스 사이트 스크립팅) 공격 방지를 위한 헤더 추가
                .addHeaderWriter((request, response) -> {
                    response.setHeader("X-XSS-Protection", "1; mode=block");
                })
            )
            // CSRF(크로스 사이트 요청 위조) 보호 기능 비활성화
            .csrf(csrf -> csrf.disable())
            // 세션 관리 설정
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                //maximunSession 설정 제거하여 다중 로그인 가능하도록 함 
            );
        return http.build();
    }

    // 비밀번호 암호화를 위한 인코더를 빈으로 등록
    @Bean 
    public PasswordEncoder passwordEncoder() {      
        // BCrypt 해싱 알고리즘을 사용하는 인코더 반환
        return new BCryptPasswordEncoder(); 
    }     
}