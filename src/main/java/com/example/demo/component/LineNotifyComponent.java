package com.example.demo.component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.Article;
import com.example.demo.model.Dorama;
import com.example.demo.model.Favorite;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.DoramaRepository;
import com.example.demo.service.NotifyService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope(value= "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
@SuppressWarnings("serial")
@RequiredArgsConstructor
@Getter
@Setter
public class LineNotifyComponent {
	
	final DoramaRepository doramaRepository;
	final NotifyService notifyService;
	
	
	public void executeNotification(SiteUser user) {
		
		//if (user.getAdmin()) {
			int StartweekOfDay;
			String message;
			
			List<Dorama> allDoramas = doramaRepository.findAll();
			
			
			Calendar today = Calendar.getInstance();
			
			for (Dorama dorama:allDoramas) {
				 StartweekOfDay = dorama.getStartDay().get(Calendar.DAY_OF_WEEK);
				 
				 
				 //if (today.after(dorama.getStartDay()) && today.before(dorama.getEndDay()) && StartweekOfDay == today.get(Calendar.DAY_OF_WEEK)) {
				 		
				 		if (notifyService.todayNotifyCheck(dorama.getId())) {
				 			
				 			notifyService.insert(dorama);
				 			
				 			message = "本日は"+dorama.getStartDay().get(Calendar.HOUR_OF_DAY)+"時から"+dorama.getName()+"が放送されます!";
							
							for (Favorite f :dorama.getFavorites()) {
								String token = f.getUser().getToken();
								
								if (token != null) {	
									
									sendNotification(message,token);
								
								}
							}
				 		}
						
				// }
			}
		//}
		
	}
		
		
	 public void sendNotification(String message, String token) {
	        HttpURLConnection connection = null;
	        try {
	        	
	            URL url = new URL("https://notify-api.line.me/api/notify");
	            
	            connection = (HttpURLConnection) url.openConnection();
	            
	            connection.setDoOutput(true);
	           
	            connection.setRequestMethod("POST");
	            
	            connection.addRequestProperty("Authorization", "Bearer " + token);
	           
	            try (OutputStream outputStream = connection.getOutputStream();
	                    PrintWriter writer = new PrintWriter(outputStream)) {
	            	
	                writer.append("message=").append(URLEncoder.encode(message, "UTF-8")).flush();
	                try (InputStream is = connection.getInputStream();
	                        BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
	                       String res = r.lines().collect(Collectors.joining());
	                       if (!res.contains("\"message\":\"ok\"")) {
	                           System.out.println(res);
	                           System.out.println("なんか失敗したっぽい");
	                       }
	                   }
	            }
	        } catch (Exception e) {
	        	System.out.println(e.getMessage());
	        } finally {
	            if (connection != null) {
	                connection.disconnect();
	            }
	        }
	    }
	    

}
