package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;


import com.example.demo.validator.UniqueDorama;


import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@Entity
public class Dorama {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@UniqueDorama
	private String name;
	
	
	private Calendar startDay;
	
	private Calendar endDay;
	
	
	@ManyToOne
	private Season season;
	
	@ManyToOne
	private SiteUser creater;
	
	@OneToMany(mappedBy="dorama")
	private List<Favorite> favorites;
	
	@OneToMany(mappedBy="dorama")
	private List<DoramaFavoriteCount> favoriteCounts;
	
	@OneToMany(mappedBy="dorama")
	private List<Channel> channels;
	
	
	public String translateCalendarToString1(Calendar c) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		String stringDay = sdf.format(c.getTime());
		
		return stringDay;
	}
	
	public String translateCalendarToString2(Calendar c) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String stringDay = sdf.format(c.getTime());
			
			return stringDay;
		}
	
	public String translateCalendarToSimpleYearAndMonth(Calendar c) {
		
		return c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1);
	}
	
	
	
	
	

}
