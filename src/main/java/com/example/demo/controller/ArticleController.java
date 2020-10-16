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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.component.ChannelComponent;
import com.example.demo.component.DoramaComponent;
import com.example.demo.model.Article;
import com.example.demo.model.Reply;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ChannelRepository;
import com.example.demo.repository.ReplyRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ReplyService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserDetailsImpl;
import com.example.demo.validator.ArticleTargetIdValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ArticleController {
	
	
	private final ArticleService articleService;
	
	private final ArticleRepository articleRepository;
	
	private final ReplyService replyService;
	private final SessionService sessionService;
	private final ArticleTargetIdValidator articleTargetIdValidator;
	
	@Autowired
	ChannelComponent channelComponent;
	
	@Autowired
	DoramaComponent targetDoramaComponent;
	

	
	@GetMapping("/article_new")
	public String articleNew(@ModelAttribute("article") Article article) {
		
		return "articleNew";
	}
	
	
	@PostMapping("/article_create")
	public String articleCreate(@Validated @ModelAttribute("article") Article article,BindingResult result,@AuthenticationPrincipal UserDetailsImpl userDetail,@RequestParam("targetArticleId") Long id,Model model) {
		
		
		
		if (result.hasErrors() || !articleTargetIdValidator.articleTargetIdCheck(id)) {
			
			if (!articleTargetIdValidator.articleTargetIdCheck(id)) {
				model.addAttribute("target_error", "その番号のメッセージは存在しません");
			}
			return "articleNew";
		}
		
		articleService.insert(article, userDetail, channelComponent.getChannel());
		replyService.insert(article, id);
		return "redirect:/article_index"+channelComponent.getChannel().getId();
	}

	
	@GetMapping("/article_delete{id}")
	public String articleDelter(@PathVariable Long id) {
		articleService.delete(id);
		return "redirect:/article_index"+channelComponent.getChannel().getId();
	}
	
	@GetMapping("/article_index{id}")
	public String articleIndex(@PathVariable Long id,Model model) {
		
		sessionService.setTragetChannelComponent(id);
		model.addAttribute("channel", channelComponent.getChannel());
		model.addAttribute("articles", articleRepository.searchArticleByChannel(id));
		
		return "articleIndex";
		
	
		
	}

}
