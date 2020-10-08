package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Dorama;

public interface DoramaRepository extends JpaRepository<Dorama,Long> {

}
