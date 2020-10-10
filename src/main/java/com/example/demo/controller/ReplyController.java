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

import com.example.demo.component.ArticleComponent;
import com.example.demo.component.ChannelComponent;
import com.example.demo.model.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ReplyService;
import com.example.demo.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReplyController {
	
	@Autowired
	ChannelComponent channelComponent;
	
	@Autowired
	ArticleComponent articleComponent;
	
	
	final ArticleRepository articleRepository;
	final ArticleService articleService;
	final ReplyService replyService;
	@GetMapping("/reply_index{id}")
	public String replyIndex(@PathVariable Long id,Model model,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		model.addAttribute("targetArticle", articleRepository.findById(id).get());
		model.addAttribute("article", new Article());
		model.addAttribute("user", userDetail.getSiteUser());
		
		articleComponent.setArticle(articleRepository.findById(id).get());
		
		return "replyIndex";
		
	}
	
	@PostMapping("/reply_create")
	public String replyCreate(@Validated @ModelAttribute("article") Article article,BindingResult result,@AuthenticationPrincipal UserDetailsImpl userDetail,Model model) {
		
		if (result.hasErrors()) {
			model.addAttribute("targetArticle", articleComponent.getArticle());
			return "/replyIndex";
			
		} 
		System.out.println("content:"+article.getContent());
		System.out.println(article.getId());
		articleService.insert(article, userDetail, channelComponent.getChannel());
		replyService.insert(article, articleComponent.getArticle().getId());
		return "redirect:/reply_index"+articleComponent.getArticle().getId();
	}

}
