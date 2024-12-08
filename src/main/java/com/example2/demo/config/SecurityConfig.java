package com.example2.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration // 스프링 설정 클래스 지정, 등록된 Bean 생성 시점
@EnableWebSecurity // 스프링 보안 활성화
public class SecurityConfig { // 스프링에서 보안 관리 클래스
@Bean // 명시적 의존성 주입 : Autowired와 다름

public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .headers(headers -> headers
            .addHeaderWriter((request, response) -> {
                response.setHeader("X-XSS-Protection", "1; mode=block");
            })
        )
        .csrf(csrf -> csrf.disable())  // withDefaults() 대신 이렇게 수정
        .sessionManagement(session -> session
            .invalidSessionUrl("/session-expired")
            .maximumSessions(1)
            .maxSessionsPreventsLogin(true)
        );
    return http.build();
}
    @Bean // 암호화 설정
    public PasswordEncoder passwordEncoder() {      
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 저장
    }     
    
    
}