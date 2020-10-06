package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.validator.UniqueLogin;
import com.example.demo.validator.UserPassword;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max=60)
	@UniqueLogin
	private String name;
	
	@NotBlank
	private String password;
	
	private String token;
	
	@OneToMany(mappedBy="siteuser")
	private List<Article> articles;
	
	@OneToMany(mappedBy="siteuser")
	private List<Heart> hearts;
	
	
}
