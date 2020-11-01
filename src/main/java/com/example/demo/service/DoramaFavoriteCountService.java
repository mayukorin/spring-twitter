package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.DoramaFavoriteCountRepository;
import com.example.demo.model.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoramaFavoriteCountService {

	private final DoramaFavoriteCountRepository doramaFavoriteCountRepository;
	private final FavoriteService favoriteService;

	public List<String> collectFavoriteCountDay(String date,Long seasonId) {
		return doramaFavoriteCountRepository.collectFavoriteCountDay(date, seasonId);
	}

	public void insert(Dorama dorama) {

		DoramaFavoriteCount df = new DoramaFavoriteCount();
		Long count = favoriteService.CountFavoriteByDorama(dorama.getId());
		df.setDorama(dorama);
		df.setFavoriteCount(count);

		doramaFavoriteCountRepository.save(df);

	}

	public List<String> collectFavoriteCountDayByDay(String date,Long seasonId) {
		return doramaFavoriteCountRepository.collectFavoriteCountDayByDay(date, seasonId);
	}

	public DoramaFavoriteCount collectFavoriteCountByDoramaAndCreatedAt(String day,Long doramaId) {
		return doramaFavoriteCountRepository.collectFavoriteCountByDoramaAndCreatedAt(day, doramaId);

	}

	public List<DoramaFavoriteCount> collectDoramaFavoriteCountByDorama(Long id) {
		return doramaFavoriteCountRepository.collectDoramaFavoriteCountByDorama(id);
	}

	public void deleteById(Long id) {
		doramaFavoriteCountRepository.deleteById(id);
	}

}
