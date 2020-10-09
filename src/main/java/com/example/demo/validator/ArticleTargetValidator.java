package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.ArticleRepository;


public class ArticleTargetValidator implements ConstraintValidator<ArticleTarget,Long> {
	
private final ArticleRepository articleRepository;
	
	public ArticleTargetValidator() {
		this.articleRepository = null;
	}
	
	@Autowired
	public ArticleTargetValidator(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	@Override
	public boolean isValid(Long value,ConstraintValidatorContext context) {
		
		Long articleCount =1L;
		
		if (value!=null ) {
			
			return articleRepository == null || articleRepository.countArticleById(value) > 0;
			
		}
			
		return articleCount > 0;
		
	}

}
