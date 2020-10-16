package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ReplyRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SiteUserService {
	
	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	public void insert(SiteUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRepository.save(user);
	}
	
	public void update(SiteUser user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRepository.save(user);
	}

}
