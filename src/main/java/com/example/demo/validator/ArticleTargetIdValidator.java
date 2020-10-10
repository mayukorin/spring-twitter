package com.example.demo.validator;

import org.springframework.stereotype.Component;

import com.example.demo.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArticleTargetIdValidator {
	
	private final ArticleRepository articleRepository;
	
	public boolean articleTargetIdCheck(Long id) {
		return id == null || articleRepository.countArticleById(id) > 0;
	}

}
