package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Article;
import com.example.demo.model.Channel;
import com.example.demo.model.Dorama;
import com.example.demo.model.Reply;

import com.example.demo.repository.ChannelRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChannelService {

	private final ChannelRepository channelRepository;
	public final ArticleService articleService;
	public final ReplyService replyService;

	public void insert(Channel channel,UserDetailsImpl userdetail,Dorama dorama) {

		channel.setCreater(userdetail.getSiteUser());
		channel.setDorama(dorama);

		channelRepository.save(channel);



	}

	public void delete(Long id) {

		List<Article> articleByChannel = articleService.collectArticlesByChannelId(id);

		for (Article article:articleByChannel) {
			for (Reply r:article.getReplysForMe()) {
				replyService.deleteById(r.getId());
			}

			articleService.deleteById(article.getId());

		}

		channelRepository.deleteById(id);

	}

	public boolean deleteCheck(Long id,UserDetailsImpl userdetail) {

		return userdetail.getSiteUser().getId() == channelRepository.findById(id).get().getCreater().getId();
	}

	public List<Channel> getChannelsByDoramaId(Long id) {

		return channelRepository.findChannelByDoramaId(id);
	}

	public List<Channel> findMyCreateChannel(Long id) {

		return channelRepository.findChannelByCreater(id);
	}

	public Channel findById(Long id) {
		return channelRepository.findById(id).get();
	}

}
