package com.example2.demo.model.domain;

import lombok.*; // Lombok이라는 도구를 사용해요. 이 도구는 반복되는 코드를 줄여주는 마법 같은 역할을 해요.
import jakarta.persistence.*; // 데이터베이스와 우리 프로그램을 연결해주는 특별한 도구를 가져와요.

@Getter // 이 클래스의 모든 변수에 대해 자동으로 값을 가져오는 메소드를 만들어줘요.
@Entity // 이 클래스가 데이터베이스의 테이블과 연결된다는 걸 알려주는 표시에요.
@Table(name = "article") // 데이터베이스에서 이 클래스와 연결될 테이블 이름이 "article"이라고 알려주는 거예요.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무것도 넣지 않고 이 클래스의 객체를 만들 수 있게 해주지만, 외부에서는 함부로 만들지 못하게 해요.

public class Article {
    @Id // 이 변수가 데이터베이스에서 각 항목을 구분하는 중요한 번호라는 걸 표시해요.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이 번호를 자동으로 1씩 증가시켜 만들어달라고 데이터베이스에 부탁하는 거예요.
    @Column(name = "id", updatable = false) // 데이터베이스에서 이 변수의 이름이 'id'이고, 한 번 정해지면 바꿀 수 없다고 알려주는 거예요.
    private Long id;

    @Column(name = "title", nullable = false) // 제목을 저장하는 곳이에요. 반드시 값이 있어야 해요.
    private String title = "";

    @Column(name = "content", nullable = false) // 내용을 저장하는 곳이에요. 이것도 반드시 값이 있어야 해요.
    private String content = "";

    @Builder // 이 클래스의 객체를 만들 때 편리하게 만들 수 있게 도와주는 특별한 방법을 제공해요.
    public Article(String title, String content){
        this.title = title;
        this.content = content;
    }
    
    public void update(String title, String content) { // 제목과 내용을 새로운 값으로 바꿀 수 있게 해주는 메소드에요.
        this.title = title;
        this.content = content;
    }
        
}