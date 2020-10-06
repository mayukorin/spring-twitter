package com.example.demo.controller;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SiteUserController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/")
	public String home(Authentication loginUser,Model model,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		model.addAttribute("user_name", loginUser.getName());
		
		
		return "home";
	}
	
	@GetMapping("/user_register")
	public String register(@ModelAttribute("user") SiteUser user) {
		return "register";
	}
	
	@PostMapping("/user_register")
	public String userProcess(@Validated @ModelAttribute("user") SiteUser user,BindingResult result) {
		
		if (result.hasErrors()) {
			return "register";
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRepository.save(user);
		
		return "redirect:/login?register";
		
	}
	
	@GetMapping("/user_edit")
	public String editUser(@AuthenticationPrincipal UserDetailsImpl userDetail,Model model) {
		
		model.addAttribute("login_user", userRepository.findById(userDetail.getId()));
		
		Optional<SiteUser> u = userRepository.findById(userDetail.getId());
		
		System.out.println(u.get().getId());
		return "userEdit";
	}
	
	@PostMapping("/user_edit_register")
	public String userEdit(@Validated @ModelAttribute("login_user") SiteUser user,BindingResult result) {
		
		if (result.hasErrors()) {
			
			return "userEdit";
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRepository.save(user);
		
		return "redirect:/logout";
		
		
	}

}
