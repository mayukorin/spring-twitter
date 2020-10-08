package com.example.demo.controller;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.component.DoramaComponent;
import com.example.demo.model.Channel;
import com.example.demo.model.Dorama;
import com.example.demo.repository.ChannelRepository;
import com.example.demo.repository.DoramaRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Controller
@RequiredArgsConstructor
public class ChannelController {
	
	@Autowired
	DoramaComponent targetDoramaComponent;
	
	private final ChannelRepository channelRepository;
	private final DoramaRepository doramaRepository;
	
	@GetMapping("/channelIndex{id}")
	public String channelIndex(@PathVariable Long id,Model model) {
		
		List<Channel> channels = channelRepository.findChannelByDoramaId(id);
		model.addAttribute("channels", channels);
		
		model.addAttribute("dorama", doramaRepository.findById(id).get());
		
		targetDoramaComponent.setDorama(doramaRepository.findById(id).get());
		
		System.out.println(targetDoramaComponent.getDorama().getName());
		
		return "channelIndex";
		
	}
	
	

}
