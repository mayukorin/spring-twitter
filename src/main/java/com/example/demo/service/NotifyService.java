package com.example.demo.service;



import org.springframework.stereotype.Service;

import com.example.demo.model.Dorama;
import com.example.demo.model.Notify;
import com.example.demo.repository.NotifyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifyService {
	
	private final NotifyRepository notifyRepository;
	
	public boolean todayNotifyCheck(Long id) {
		
		Long notifyCount = notifyRepository.countNotifyByDate(id);
		
		return notifyCount == 0;
		
	}
	
	public void insert(Dorama dorama) {
		
		Notify notify = new Notify();
		notify.setDorama(dorama);
		
		notifyRepository.save(notify);
	}

}
