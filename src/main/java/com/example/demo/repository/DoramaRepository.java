package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.example.demo.model.Dorama;

public interface DoramaRepository extends JpaRepository<Dorama,Long> {
	
	@Query("select d from Dorama d where d.id = :#{#id}")
	Dorama serachDoramaById(Long id);
	
	@Query("select d from Dorama d where d.name = :#{#name}")
	Dorama searchDoramaByName(String name);
	
	@Query(value="select distinct(date_format(d.start_day,'%Y-%m')) from dorama as d order by d.start_day desc",nativeQuery=true)
	List<String> collectStartMonth();
	
	@Query(value="select date_format(d.start_day,'%Y-%m') from dorama as d where d.id = :#{#id}",nativeQuery=true)
	String GetStartMonth(Long id);
	
	@Query(value="select * from Dorama as d where date_format(d.start_day,'%Y-%m') = :#{#datee} order by d.start_day desc",nativeQuery=true)
	List<Dorama> collectDoramaByStart(String datee);
	
	@Query(value="select d.name from Dorama as d where date_format(d.start_day,'%Y-%m') = :#{#datee}",nativeQuery=true)
	List<String> collectDoramaNameByStart(String datee);
	
	@Query("select d from Dorama d,Favorite f where f.user.id = :#{#id} and d.id = f.dorama.id")
	List<Dorama> collectDoramaFavoriteByUser(Long id);
	
	@Query("select d from Dorama d where d.season.id = :#{#id}")
	List<Dorama> collectDoramaBySeason(Long id);
	
	@Query(value="select * from Dorama as d where d.name like %:#{#keyword}%",nativeQuery=true)
	List<Dorama> collectDoramaByKeyword(String keyword);
	
	@Query("select d from Dorama d where d.creater.id = :#{#id}")
	List<Dorama> collectDoramaByCreater(Long id);
	
	
}
