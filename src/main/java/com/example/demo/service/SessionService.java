package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.component.DoramaComponent;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.DoramaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {
	
	private final DoramaRepository doramaRepository;
	
	@Autowired
	DoramaComponent targetDoramaComponent;
	
	public void setTargetDoramaComponent(Long id) {
		targetDoramaComponent.setDorama(doramaRepository.findById(id).get());
	}
	

}
