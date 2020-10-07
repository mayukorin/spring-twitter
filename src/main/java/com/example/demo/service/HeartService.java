package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Article;
import com.example.demo.model.Heart;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.HeartRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HeartService {

	private final HeartRepository heartRepository;
	private final ArticleRepository articleRepository;
	



	public void insert(UserDetailsImpl userdetail,Long id) {

		Heart newHeart = new Heart();
		
		newHeart.setSiteuser(userdetail.getSiteUser());
		newHeart.setArticle(articleRepository.serachArticleById(id));
		
		heartRepository.save(newHeart);
	}
	
	public void delete(UserDetailsImpl userdetail,Long id) {
	
		Heart heart = heartRepository.findBySiteuserAndArticle(userdetail.getSiteUser(), articleRepository.serachArticleById(id));
		
		heartRepository.deleteById(heart.getId());
		
	}
	
	
}
