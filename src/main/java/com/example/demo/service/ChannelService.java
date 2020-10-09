package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Article;
import com.example.demo.model.Channel;
import com.example.demo.model.Dorama;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ChannelRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChannelService {
	
	private final ChannelRepository channelRepository;
	public final ArticleRepository articleRepository;
	
	public void insert(Channel channel,UserDetailsImpl userdetail,Dorama dorama) {
		
		channel.setCreater(userdetail.getSiteUser());
		channel.setDorama(dorama);
		
		channelRepository.save(channel);
		
	}
	
	public void delete(Long id) {
		
		List<Article> articleByChannel = articleRepository.searchArticleByChannel(id);
		
		for (Article article:articleByChannel) {
			articleRepository.delete(article);
		}
		
		channelRepository.deleteById(id);
		
	}
	
	public boolean deleteCheck(Long id,UserDetailsImpl userdetail) {
		
		return userdetail.getSiteUser().getId() == channelRepository.findById(id).get().getCreater().getId();
	}

}
