package com.example.demo.service;


import com.example.demo.model.SiteUser;
import com.example.demo.model.Dorama;
import com.example.demo.model.Favorite;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.FavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {

	private final FavoriteRepository favoriteRepository;

	public void save(SiteUser user,Dorama dorama) {

		Favorite f = new Favorite();

		f.setDorama(dorama);
		f.setUser(user);

		favoriteRepository.save(f);

	}
	public void delete(SiteUser user,Dorama dorama) {

		Favorite deleteFavorite = favoriteRepository.FindFavoriteByUserAndDorama(user.getId(), dorama.getId());
		favoriteRepository.deleteById(deleteFavorite.getId());
	}

	public Long CountFavoriteByUserAndDorama(Long siteuserId,Long doramaId) {

		return favoriteRepository.CountFavoriteByUserAndDorama(siteuserId,doramaId);
	}

	public List<Favorite> collectFavoritesByDorama(Long id) {
		return favoriteRepository.collectFavoritesByDorama(id);
	}

	public Long CountFavoriteByDorama(Long id) {
		return favoriteRepository.CountFavoriteByDorama(id);
	}

	public List<SiteUser> collectFavoriteUsers(Long id) {
		return favoriteRepository.collectFavoriteUsers(id);
	}

	public void deleteById(Long id) {
		favoriteRepository.deleteById(id);
	}


}
