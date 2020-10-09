package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.component.ChannelComponent;
import com.example.demo.model.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ChannelRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ArticleController {
	
	
	private final ArticleService articleService;
	private final ChannelRepository channelRepository;
	private final ArticleRepository articleRepository;
	
	@Autowired
	ChannelComponent channelComponent;

	
	@GetMapping("/article_new")
	public String articleNew(@ModelAttribute("article") Article article) {
		return "articleNew";
	}
	
	
	@PostMapping("/article_create")
	public String articleCreate(@Validated @ModelAttribute("article") Article article,BindingResult result,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		if (result.hasErrors()) {
			
			return "articleNew";
		} else {
			
			articleService.insert(article, userDetail,channelComponent.getChannel());
			return "redirect:/article_index"+channelComponent.getChannel().getId();
			
			
			
		}
	}

	
	@GetMapping("/article_delete{id}")
	public String articleDelter(@PathVariable Long id) {
		articleService.delete(id);
		return "redirect:/";
	}
	
	@GetMapping("/article_index{id}")
	public String articleIndex(@PathVariable Long id,Model model) {
		
		channelComponent.setChannel(channelRepository.findById(id).get());
		
		model.addAttribute("channel", channelComponent.getChannel());
		model.addAttribute("articles", articleRepository.searchArticleByChannel(id));
		
		return "articleIndex";
		
	
		
	}

}
