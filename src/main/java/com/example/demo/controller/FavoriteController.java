package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.component.DoramaComponent;
import com.example.demo.service.FavoriteService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FavoriteController {
	
	private final FavoriteService favoriteService;
	private final SessionService sessionService;
	
	@Autowired
	DoramaComponent targetDoramaComponent;
	
	@GetMapping("/favorite_create")
	public String favoriteCreate(@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		
		favoriteService.save(userDetail.getSiteUser(), sessionService.getTargetDoramaComponent().getDorama());
		return "redirect:/channelIndex"+targetDoramaComponent.getDorama().getId();
	}
	
	@GetMapping("/favorite_delete")
	public String favoriteDelete(@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		favoriteService.delete(userDetail.getSiteUser(), sessionService.getTargetDoramaComponent().getDorama());
		return "redirect:/channelIndex"+targetDoramaComponent.getDorama().getId();
	}

}
