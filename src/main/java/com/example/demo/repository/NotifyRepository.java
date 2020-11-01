package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Notify;

public interface NotifyRepository extends JpaRepository<Notify,Long>{
	
	@Query("select count(n) from Notify as n where n.dorama.id = :#{#id} and date_format(n.created_at,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d')")
	Long countNotifyByDate(Long id);
	
	
	

}
