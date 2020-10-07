package com.example.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.example.demo.service.HeartService;
import com.example.demo.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HeartController {
	
	private final HeartService heartService;
	
	
	@GetMapping("/heart_delete{id}") 
	public String heartDelete(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		heartService.delete(userDetail, id);
		
		return "redirect:/";
		
		
		
	}
	
	@GetMapping("/heart_create{id}") 
	public String heartCreate(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		
		heartService.insert(userDetail, id);
		
		return "redirect:/";
		
		
		
	}

}
