package com.example2.demo.model.service;
import lombok.*; // 어노테이션 자동 생성
import com.example2.demo.model.domain.Board;

@NoArgsConstructor //기본 생성자 추가
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자 추가
@Data //getter setter equals 등 자동 생성
public class AddBoardRequest { //DTO 클래스
    private String title;
    private String content;
    private String user;
    private String newdate;
    private String count;
    private String likecount;

    public Board toEntity() {
        return Board.builder()
        .title(title)
        .content(content)
        .user(user)
        .newdate(newdate)
        .count(count)
        .likecount(likecount)
        .build();
    }
}