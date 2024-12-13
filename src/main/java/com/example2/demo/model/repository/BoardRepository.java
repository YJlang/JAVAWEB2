package com.example2.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example2.demo.model.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    List<Board> findTop5ByOrderByViewCountDesc();
}