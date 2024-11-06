package com.example2.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example2.demo.model.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}