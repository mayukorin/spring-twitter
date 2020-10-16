package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.component.ArticleComponent;
import com.example.demo.component.ChannelComponent;
import com.example.demo.component.DoramaComponent;
import com.example.demo.model.Channel;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ChannelRepository;
import com.example.demo.repository.DoramaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {
	
	private final DoramaRepository doramaRepository;
	private final ChannelRepository channelRepository;
	private final ArticleRepository articleRepository;
	
	
	@Autowired
	DoramaComponent targetDoramaComponent;
	
	@Autowired
	ChannelComponent channelComponent;
	
	@Autowired
	ArticleComponent articleComponent;
	public void setTargetDoramaComponent(Long id) {
		targetDoramaComponent.setDorama(doramaRepository.findById(id).get());
	}
	
	public void setTragetChannelComponent(Long id) {
		
		Channel targetChannel = channelRepository.findById(id).get();
		channelComponent.setChannel(targetChannel);
		
	}
	
	public void setArticleComponent(Long id) {
		articleComponent.setArticle(articleRepository.findById(id).get());
	}

}
