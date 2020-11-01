package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.example.demo.model.DoramaFavoriteCount;

public interface DoramaFavoriteCountRepository extends JpaRepository<DoramaFavoriteCount,Long> {
	
	@Query(value="select distinct(date_format(df.created_at,'%Y-%m-%d')) from dorama_favorite_count df,dorama d where date_format(df.created_at,'%Y-%m') = :#{#datee} and df.dorama_id = d.id and d.season_id = :#{#id}",nativeQuery=true)
	List<String> collectFavoriteCountDay(String datee,Long id);
	
	@Query(value="select distinct(date_format(df.created_at,'%Y-%m-%d')) from dorama_favorite_count df,dorama d where date_format(df.created_at,'%Y-%m-%d') = :#{#datee} and df.dorama_id = d.id and d.season_id = :#{#id}",nativeQuery=true)
	List<String> collectFavoriteCountDayByDay(String datee,Long id);
	
	@Query(value="select * from dorama_favorite_count df where date_format(df.created_at,'%Y-%m-%d') = :#{#date} and df.dorama_id = :#{#id}",nativeQuery=true)
	DoramaFavoriteCount collectFavoriteCountByDoramaAndCreatedAt(String date,Long id);
	
	@Query("select df from DoramaFavoriteCount as df where df.dorama.id = :#{#id}")
	List<DoramaFavoriteCount> collectDoramaFavoriteCountByDorama(Long id);

}
