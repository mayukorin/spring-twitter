package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.SeasonName;

public interface SeasonNameRepository extends JpaRepository<SeasonName,Long> {

}
