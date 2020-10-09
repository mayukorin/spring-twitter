package com.example.demo.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Article;



public interface ArticleRepository extends JpaRepository<Article,Long> {
	

	@Query("select a from Article a where a.siteuser.id = :#{#id}")
	Collection<Article> serachArticleBySiteuser(Long id);
	
	@Query("select a from Article a where a.id = :#{#id}")
	Article serachArticleById(Long id);
	
	@Query("select a from Article as a where a.channel.id=:#{#id}")
	List<Article> searchArticleByChannel(Long id);
	
	
}
