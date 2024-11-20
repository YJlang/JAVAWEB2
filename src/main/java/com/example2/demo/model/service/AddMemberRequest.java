package com.example2.demo.model.service;

import com.example2.demo.model.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class AddMemberRequest {
    @NotBlank(message = "이름은 필수 입력값입니다")
    @Pattern(regexp = "^[가-힣x|X]{2,}$", message = "이름은 한글 2글자 이상이어야 합니다")
    private String name;

    @NotBlank(message = "이메일은 필수 입력값입니다")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", 
            message = "비밀번호는 8자 이상이며, 대소문자, 특수문자를 포함해야 합니다")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력값입니다")
    private String passwordConfirm;

    @NotBlank(message = "나이는 필수 입력값입니다")
    @Pattern(regexp = "^(?:1[9]|[2-8][0-9]|90)$", message = "나이는 19세 이상 90세 이하여야 합니다")
    private String age;

    @NotBlank(message = "휴대폰 번호는 필수 입력값입니다")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다")
    private String mobile;

    @NotBlank(message = "주소는 필수 입력값입니다")
    private String address;

    // 비밀번호 일치 여부 확인
    public boolean isPasswordMatch() {
        return password.equals(passwordConfirm);
    }

    // Member 엔티티로 변환
    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password) // 실제 서비스에서는 암호화 필요
                .age(age)       
                .mobile(mobile)
                .address(address)   
                .build();   
    }
}