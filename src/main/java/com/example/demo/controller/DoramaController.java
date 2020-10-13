package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.component.LineNotifyComponent;
import com.example.demo.repository.SeasonRepository;
import com.example.demo.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DoramaController {
	
	final SeasonRepository seasonRepository;
	
	@Autowired
	LineNotifyComponent line;
	
	@GetMapping("/topPage")
	public String topPage(Model model,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		line.executeNotification(userDetail.getSiteUser());
		model.addAttribute("seasons", seasonRepository. findByAllSeasonOrderBySeason());
		
		
		return "topPage";
	}

}
