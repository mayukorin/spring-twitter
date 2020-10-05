package com.example.demo.model;





import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Heart {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private SiteUser siteuser;
	
	@ManyToOne
	private Article article;
	
	
	

}
