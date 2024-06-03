package com.apple.shoppingmallboot.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;

    @GetMapping("/article")
    String article(Model model){
        List<Article> result = articleRepository.findAll();
        model.addAttribute("articles", result);
        return "article.html";
    }
}
