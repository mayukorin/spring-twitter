package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Article;
import com.example.demo.model.Heart;
import com.example.demo.model.SiteUser;

public interface HeartRepository extends JpaRepository<Heart,Long>  {
	
	int countByArticle(Article article);
	
	@Query("select h from Heart as h where h.siteuser = :#{#user} and h.article= :#{#article}")
	Heart findBySiteuserAndArticle(SiteUser user,Article article);

}
