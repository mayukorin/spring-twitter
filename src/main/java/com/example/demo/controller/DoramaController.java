package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repository.SeasonRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DoramaController {
	
	final SeasonRepository seasonRepository;
	
	@GetMapping("/topPage")
	public String topPage(Model model) {
		
		model.addAttribute("seasons", seasonRepository. findByAllSeasonOrderBySeason());
		
		
		return "topPage";
	}

}
