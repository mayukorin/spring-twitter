package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Article;
import com.example.demo.model.Channel;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleService {
	
	
	private final ArticleRepository articleRepository;
	
	public void insert(Article article,UserDetailsImpl userdetail,Channel channel) {
		
		System.out.println("すっきり");
		article.setSiteuser(userdetail.getSiteUser());
		System.out.println("バゲット");
		article.setChannel(channel);
		System.out.println("ヒルナンデス！");
		//System.out.println(article.getTargetArticleId());
		articleRepository.save(article);
		System.out.println("ヒルナンデスa！");
	}
	
	public void delete(Long id) {
		articleRepository.deleteById(id);
	}
	
	

}
