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
import com.example2.demo.model.service.BlogService;
import com.example2.demo.model.service.AddArticleRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;


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

    

    @PostMapping("/api/articles")
    public String addArticle(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/article_list";
    }

    //@ResponseBody
    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/article_list"; // 글 수정 이후 .html 연결
    }

    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/article_list";
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ModelAndView handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
            ModelAndView mv = new ModelAndView("/article_string_error");
            mv.addObject("errorMessage", "잘못된 요청입니다(문자열 오류). 올바른 ID를 입력해주세요.");
            return mv;
        }
    }

}