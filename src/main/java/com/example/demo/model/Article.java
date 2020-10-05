package com.example.demo.model;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Article {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max=140)
	private String content;
	
	private Timestamp created_at;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy="article")
	private List<Heart> hearts;
	
	
	
	@PrePersist
	public void onPrePersist() {
		setCreated_at(new Timestamp(System.currentTimeMillis()));
	}
	
	
	

}
