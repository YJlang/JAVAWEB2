package com.example2.demo.controller;

// import com.example2.demo.model.domain.Article;
// import com.example2.demo.model.service.AddArticleRequest;
//import com.example2.demo.model.service.BlogService;
import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class BlogRestController {
    //private final BlogService blogService;

    @GetMapping("/favicon.ico")
    @ResponseBody
    public void favicon() {
        //아무 작업도 하지 않음
    }

    // 기존 RestController 방식은 주석 처리
    /*
    @PostMapping("/api/articles")
    @ResponseBody
    public ResponseEntity<Article> addArticle(@ModelAttribute AddArticleRequest request){
        Article saveArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
    }
    */
}