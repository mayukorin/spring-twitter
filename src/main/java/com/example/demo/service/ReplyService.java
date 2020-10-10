package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.ReplyRepository;
import com.example.demo.model.Article;
import com.example.demo.model.Reply;
import com.example.demo.repository.ArticleRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {
	
	private final ReplyRepository replyRepository;
	private final ArticleRepository articleRepository;
	
	public void insert(Article article,Long targetArticleId) {
		
		if (targetArticleId != null) {
		
			Reply reply = new Reply();
			
			reply.setReplyArticle(article);
			
			reply.setTargetArticle(articleRepository.findById(targetArticleId).get());
			
			replyRepository.save(reply);
		}
	}

}
