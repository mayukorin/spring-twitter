package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Channel;

public interface ChannelRepository extends JpaRepository<Channel,Long> {
	
	@Query("select c from Channel as c where c.dorama.id=:#{#id}")
	List<Channel> findChannelByDoramaId(Long id);
	

}
