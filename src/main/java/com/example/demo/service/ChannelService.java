package com.example.demo.service;

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
	public void insert(Channel channel,UserDetailsImpl userdetail,Dorama dorama) {
		
		channel.setCreater(userdetail.getSiteUser());
		channel.setDorama(dorama);
		
		channelRepository.save(channel);
		
	}

}
