package com.example2.demo.controller;
import com.example2.demo.model.domain.Article;
import com.example2.demo.model.service.AddArticleRequest;
import com.example2.demo.model.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RequiredArgsConstructor
@RestController //@controller + @responeBody



public class BlogRestController {
    private final BlogService blogService;

    @GetMapping("/favicon.ico")
    public void favicon() {
        //아무 작업도 하지 않음
    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@ModelAttribute AddArticleRequest request){
        Article saveArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
    }
    
    
}


