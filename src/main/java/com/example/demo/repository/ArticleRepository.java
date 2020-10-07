package com.example.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Article;
import com.example.demo.model.SiteUser;


public interface ArticleRepository extends JpaRepository<Article,Long> {
	

	@Query("select a from Article a where a.siteuser = :#{#user}")
	Collection<Article> serachArticleBySiteuser(SiteUser user);
}
