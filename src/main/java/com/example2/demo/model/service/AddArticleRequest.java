package com.example2.demo.model.service;
import lombok.*; // 어노테이션 자동 생성
import com.example2.demo.model.domain.Article;

@NoArgsConstructor //기본 생성자 추가
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자 추가
@Data //getter setter equals 등 자동 생성
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder()
        .title(title)
        .content(content)
        .build();
    }
}