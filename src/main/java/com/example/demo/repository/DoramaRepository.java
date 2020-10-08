package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.example.demo.model.Dorama;

public interface DoramaRepository extends JpaRepository<Dorama,Long> {
	
	@Query("select d from Dorama d where d.id = :#{#id}")
	Dorama serachDoramaById(Long id);
	

}
