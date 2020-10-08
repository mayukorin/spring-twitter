package com.example.demo.model;

import java.util.Calendar;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import org.springframework.scheduling.annotation.Scheduled;

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
	
	
	@Scheduled(cron = "0 0 10 * * *", zone = "Asia/Tokyo") 
	public void executeNotification() {
		
		Calendar today = Calendar.getInstance();
		
		int StartweekOfDay = startDay.get(Calendar.DAY_OF_WEEK);
		
		
		if (today.after(startDay) && today.before(endDay) && StartweekOfDay == today.get(Calendar.DAY_OF_WEEK)) {
			System.out.println("ok");
		}
	}
	

}
