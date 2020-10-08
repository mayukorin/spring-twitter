package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Season;

public interface SeasonRepository extends JpaRepository<Season,Long> {
	
	@Query("select s from Season as s order by s.year,s.seasonName.id desc")
	List<Season> findByAllSeasonOrderBySeason();

}
