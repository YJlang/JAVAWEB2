package com.example2.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example2.demo.model.domain.TestDB;

/**
 * TestRepository 인터페이스는 TestDB 엔티티에 대한 데이터베이스 작업을 수행하는 역할을 합니다.
 * 이 인터페이스는 Spring Data JPA의 JpaRepository를 확장하여 기본적인 CRUD 작업과 페이징, 정렬 기능을 제공받습니다.
 * 
 * 주요 기능:
 * 1. TestDB 엔티티의 생성, 읽기, 수정, 삭제 (CRUD) 작업
 * 2. 이름으로 TestDB 엔티티 검색
 * 
 * @Repository 어노테이션은 이 인터페이스가 Spring의 데이터 접근 계층의 컴포넌트임을 나타냅니다.
 */
@Repository
public interface TestRepository extends JpaRepository<TestDB, Long> {
    /**
     * 주어진 이름으로 TestDB 엔티티를 검색합니다.
     * 
     * @param name 검색할 TestDB 엔티티의 이름
     * @return 찾은 TestDB 엔티티, 없으면 null 반환
     */
    TestDB findByName(String name);
}
