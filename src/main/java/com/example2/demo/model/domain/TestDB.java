package com.example2.demo.model.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity // TestDB 클래스를 JPA 엔티티로 지정. 이 클래스는 데이터베이스 테이블과 매핑됨
@Table(name = "testdb") // 이 엔티티와 매핑될 데이터베이스 테이블 이름을 'testdb'로 지정
@Data // Lombok 어노테이션. 자동으로 getter, setter, equals, hashCode, toString 메서드 생성

public class TestDB {
    @Id // 이 필드가 테이블의 기본 키(Primary Key)임을 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 값을 자동으로 생성. IDENTITY 전략은 데이터베이스에 위임
    private Long id; // 엔티티의 고유 식별자. Long 타입으로 선언

    @Column(nullable = true) // 이 필드가 데이터베이스 컬럼과 매핑됨을 나타냄. nullable=true는 이 컬럼이 NULL 값을 허용함을 의미
    private String name; // 사용자의 이름을 저장하는 필드
}
