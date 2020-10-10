package com.example.demo.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.demo.component.LineNotifyComponent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Dorama {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private Calendar startDay;
	
	private Calendar endDay;
	
	
	@ManyToOne
	private Season season;
	
	@OneToMany(mappedBy="dorama")
	private List<Favorite> favorites;
	
	
	
	
	

}
