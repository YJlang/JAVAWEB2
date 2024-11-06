package com.example2.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example2.demo.model.domain.TestDB;
import com.example2.demo.model.repository.TestRepository;
import lombok.RequiredArgsConstructor;

// findByName은 마치 도서관에서 책을 찾는 것과 같아요!
// 예를 들어, "홍길동"이라는 이름을 찾으면 도서관(데이터베이스)에서 
// "홍길동"이라는 이름이 적힌 카드(정보)를 가져오는 거예요.
// 이건 스프링이 제공하는 내장 함수가 아니라, JPA가 이름 규칙에 따라 
// 자동으로 만들어주는 메서드예요!
@Service // 서비스 계층 명시 스프링 내부 자동 등록됨
@RequiredArgsConstructor // 생성자 생성
public class TestService {
    @Autowired // 객체 주입 자동화
    private TestRepository testRepository;
    
    // 마치 도서관 사서 선생님께 "홍길동"이라는 이름이 적힌 카드를 찾아달라고 부탁하는 것처럼
    // 데이터베이스에서 그 이름의 정보를 찾아서 가져와요!
    public TestDB findByName(String name) {
        return (TestDB) testRepository.findByName(name);
    }
}
