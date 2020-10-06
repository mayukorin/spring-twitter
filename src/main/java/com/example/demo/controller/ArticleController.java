package com.example.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.model.Article;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ArticleController {
	
	private final ArticleRepository articleRepository;
	
	@GetMapping("/article_new")
	public String articleNew(@ModelAttribute("article") Article article) {
		return "articleNew";
	}

}
