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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.component.DoramaComponent;
import com.example.demo.model.Article;
import com.example.demo.model.Channel;

import com.example.demo.repository.ChannelRepository;
import com.example.demo.repository.DoramaRepository;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ChannelService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ChannelController {
	
	@Autowired
	DoramaComponent targetDoramaComponent;
	
	private final ChannelRepository channelRepository;
	private final DoramaRepository doramaRepository;
	private final FavoriteRepository favoriteRepository;
	
	private final ArticleService articleService;
	private final ChannelService channelService;
	private final SessionService sessionService;
	
	@GetMapping("/channelIndex{id}")
	public String channelIndex(@PathVariable Long id,Model model,@ModelAttribute("deleteError") String deleteError,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		sessionService.setTargetDoramaComponent(id);
		
		model.addAttribute("channels", channelRepository.findChannelByDoramaId(id));
		model.addAttribute("dorama", doramaRepository.findById(id).get());
		
		model.addAttribute("favoriteFlag", favoriteRepository.CountFavoriteByUserAndDorama(userDetail.getSiteUser().getId(),id));
		
		
		return "channelIndex";
		
	}
	
	@GetMapping("/channel_new")
	public String channelNew(Model model, @ModelAttribute Article article) {
		
		
		model.addAttribute("channel",new Channel());
		
		return "channelNew";
	}
	
	@PostMapping("/channel_create")
	public String channelCreate(RedirectAttributes redirectAttributes,@Validated @ModelAttribute("article") Article article,BindingResult resultArticle,@Validated @ModelAttribute("channel") Channel channel,BindingResult resultChannel,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		
		if (resultArticle.hasErrors() || resultChannel.hasErrors()) {
			return "channelNew";
		}
		channelService.insert(channel, userDetail, targetDoramaComponent.getDorama());
		articleService.insert(article, userDetail,channel);
		
		return "redirect:/channelIndex"+targetDoramaComponent.getDorama().getId();
	}
	
	@GetMapping("/channel_delete{id}")
	public String channelDelete(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetail,Model model,RedirectAttributes redirectAttributes) {
		
		
		channelService.delete(id);
		return "redirect:/channelIndex"+targetDoramaComponent.getDorama().getId();
	}
	
	

}
