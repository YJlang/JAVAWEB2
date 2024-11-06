package com.example2.demo.model.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example2.demo.model.domain.Article;


@Repository
public interface BlogRepository extends JpaRepository<Article, Long>{
    /**
     * 주어진 제목(title)으로 게시글(Article)을 검색하는 메서드입니다.
     * Spring Data JPA의 명명 규칙을 따라 메서드 이름으로 쿼리가 자동 생성됩니다.
     *
     * @param title 검색할 게시글의 제목
     * @return 찾은 게시글(Article) 엔티티, 없으면 null 반환
     */
    Article findByTitle(String title);
}