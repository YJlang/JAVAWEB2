package com.example2.demo.model.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example2.demo.model.domain.Article;
import com.example2.demo.model.domain.Board;
import com.example2.demo.model.repository.BlogRepository;
import com.example2.demo.model.repository.BoardRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor // 생성자 자동 생성(부분)
public class BlogService {
    @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능
    private final BlogRepository blogRepository;
    @Autowired
    private final BoardRepository boardRepository; // 리포지토리 선언

    public List<Article> findAll() { // 게시판 전체 목록 조회
        return blogRepository.findAll(); // 모든 게시글을 조회하여 반환
    }

    public List<Board> findAllBoard() { // 게시판 전체 목록 조회
        return boardRepository.findAll(); // 모든 게시글을 조회하여 반환
    }

    public Optional<Board> findByIdBoard(Long id) { // ID로 게시글 조회
        return boardRepository.findById(id); // 주어진 ID로 게시글을 찾아 반환
    }

    

    public Article save(AddArticleRequest request){ // 새로운 게시글 저장
        return blogRepository.save(request.toEntity()); // 요청을 엔티티로 변환하여 저장
    }

    public Optional<Article> findById(Long id){ // ID로 게시글 조회
        return blogRepository.findById(id); // 주어진 ID로 게시글을 찾아 반환
    }

    public void update(Long id, AddArticleRequest request) { // 게시글 수정
        Optional<Article> optionalArticle = blogRepository.findById(id); // 주어진 ID로 게시글을 찾음
        optionalArticle.ifPresent(article -> { // 게시글이 존재하면
            article.update(request.getTitle(), request.getContent()); // 제목과 내용을 업데이트
            blogRepository.save(article); // 업데이트된 게시글을 저장
        });
    }

    public void updateBoard(Long id, AddBoardRequest request) { // 게시글 수정
        Optional<Board> optionalBoard = boardRepository.findById(id); // 주어진 ID로 게시글을 찾음
        optionalBoard.ifPresent(board -> { // 게시글이 존재하면
            board.update(request.getTitle(), request.getContent(), request.getUser(), request.getNewdate(), request.getCount(), request.getLikecount()); // 제목과 내용을 업데이트
            boardRepository.save(board); // 업데이트된 게시글을 저장
        });
    }

    public void delete(Long id) { // 게시글 삭제
        blogRepository.deleteById(id); // 주어진 ID로 게시글을 삭제
    }

    public void deleteBoard(Long id) { // 게시글 삭제
        boardRepository.deleteById(id); // 주어진 ID로 게시글을 삭제
    }
}