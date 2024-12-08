package com.example2.demo.controller;

import java.util.List;
import java.util.Optional;

//import org.apache.el.stream.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.example2.demo.model.domain.Article;
import com.example2.demo.model.domain.Board;
import com.example2.demo.model.service.BlogService;

import jakarta.servlet.http.HttpSession;

import com.example2.demo.model.service.AddArticleRequest;
import com.example2.demo.model.service.AddBoardRequest;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
//@ResponseBody
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/article_list")
    public String article_list(Model model) {
        List<Article> list = blogService.findAll(); // 게시판 리스트
        model.addAttribute("articles", list); // 모델에 추가
        return "article_list"; // .HTML 연결
    }
    // 게시판 목록을 보여주는 페이지 매핑
    @GetMapping("/board_list") 
    public String board_list(Model model, @RequestParam(defaultValue = "0") int page, // 페이지 번호(기본값 0)
                                          @RequestParam(defaultValue = "") String keyword, // 검색어(기본값 빈문자열)
                                          HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        String userId = (String) session.getAttribute("userId");
        String email = (String) session.getAttribute("email");

        // 로그인 안된 경우 로그인 페이지로 리다이렉트
        if (userId == null) {
            return "redirect:/login";
        }

        // 현재 로그인된 사용자 ID 출력 
        System.out.println("세션 userId: " + userId);

        // 한 페이지당 보여줄 게시글 수
        int pageSize = 3;
        
        // 페이징 처리를 위한 객체 생성
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Board> list;

        // 검색어가 없으면 전체 목록, 있으면 검색 결과 가져오기
        if (keyword.isEmpty()) {
            list = blogService.findAllBoard(pageable);
        } 
        else {
            list = blogService.searchByKeyword(keyword, pageable);
        }

        // 현재 페이지의 시작 번호 계산
        int startNum = (page * pageSize) + 1;

        // 화면에 전달할 데이터들을 모델에 추가
        model.addAttribute("startNum", startNum); // 시작 번호
        model.addAttribute("email", email); // 사용자 이메일
        model.addAttribute("boards", list); // 게시글 목록 
        model.addAttribute("totalPages", list.getTotalPages()); // 전체 페이지 수
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("keyword", keyword); // 검색어
        model.addAttribute("startNum", startNum); // 시작 번호

        // board_list.html 페이지 반환
        return "board_list";
    }


    @GetMapping("/article_edit/{id}") // 게시판 링크 지정
    public String article_edit(Model model, @PathVariable Long id) {
        Optional<Article> list = blogService.findById(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("article", list.get()); // 존재하면 Article 객체를 모델에 추가
        } 
        else {
        // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
                return "/article_error"; // 오류 처리 페이지로 연결
        }
        return "article_edit"; // .HTML 연결
    }

    @GetMapping("/board_edit/{id}") // 게시판 링크 지정
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findByIdBoard(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("board", list.get()); // 존재하면 Board 객체를 모델에 추가
        } else {
            return "article_error"; // 오류 처리 페이지로 연결
        }
        return "board_edit"; // .HTML 연결
    }

    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findByIdBoard(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("boards", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가
        } else {
            // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "article_error"; // 오류 처리 페이지로 연결
        }
        return "board_view"; // .HTML 연결
    }

    @GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }
    
    // 새 게시글 추가
    @PostMapping("/api/articles")
    public String addArticle(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/article_list"; // 게시글 추가 후 목록 페이지로 리다이렉트
    }

    @PostMapping("/api/boards")
    public String addBoard(@ModelAttribute AddBoardRequest request) {
        blogService.saveBoard(request);
        return "redirect:/board_list";
    }

    // @PostMapping("/api/boards")
    // public String addBoard(@ModelAttribute AddBoardRequest request) {
    //     blogService.saveBoard(request);
    //     return "redirect:/board_list"; // 게시글 추가 후 목록 페이지로 리다이렉트
    // }

    // 게시글 수정
    //@ResponseBody
    @PutMapping("/api/article_edit/{id}") // PUT 방식으로 /api/article_edit/게시글번호 주소로 요청이 오면
    public String updateArticle(
        @PathVariable Long id,  // URL에서 게시글 번호를 가져와서
        @ModelAttribute AddArticleRequest request // 수정할 내용을 요청에서 받아옵니다
    ) {
        blogService.update(id, request); // 블로그 서비스를 통해 게시글을 수정하고
        return "redirect:/article_list"; // 수정이 끝나면 게시글 목록 페이지로 돌아갑니다
    }

    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(
        @PathVariable Long id,
        @ModelAttribute AddBoardRequest request
    ) {
        blogService.updateBoard(id, request);
        return "redirect:/board_list";
    }

    // 게시글 삭제
    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/article_list"; // 게시글 삭제 후 목록 페이지로 리다이렉트
    }

    @DeleteMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        blogService.deleteBoard(id);
        return "redirect:/board_list";
    }

    // 전역 예외 처리
    @ControllerAdvice
    public class GlobalExceptionHandler {
        // ID가 잘못된 형식일 때 예외 처리
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ModelAndView handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
            ModelAndView mv = new ModelAndView("/article_string_error");
            mv.addObject("errorMessage", "잘못된 요청입니다(문자열 오류). 올바른 ID를 입력해주세요.");
            return mv; // 에러 페이지로 이동
        }
        // MethodArgumentTypeMismatchException 예외는 주로 잘못된 형식의 매개변수가 전달될 때 발생합니다.
        // 예를 들어, 숫자형 ID가 필요한 곳에 문자열이 전달되었을 때 이 예외가 발생합니다.

        // 기타 예외 처리
        @ExceptionHandler(Exception.class)
        public ModelAndView handleException(Exception ex) {
            ModelAndView mv = new ModelAndView("/article_string_error");
            mv.addObject("errorMessage", "예기치 않은 오류가 발생했습니다. 다시 시도해주세요.");
            return mv; // 에러 페이지로 이동
        }
        // Exception 예외는 모든 종류의 예외를 포괄적으로 처리합니다.
        // 특정 예외를 처리하지 못한 경우 이 핸들러가 작동하여 일반적인 오류 메시지를 반환합니다.
    }
    // 이 예외 처리기는 모든 컨트롤러에서 발생하는 MethodArgumentTypeMismatchException 예외를 처리합니다.
    // article_edit에서만 작동하는 이유는 article_edit 메서드에서 @PathVariable Long id를 사용하기 때문입니다.
    // 만약 잘못된 형식의 ID가 전달되면 MethodArgumentTypeMismatchException 예외가 발생하고, 이 예외 처리기가 작동하게 됩니다.
    // 다른 메서드에서도 @PathVariable Long id를 사용하면 동일하게 작동합니다.

}