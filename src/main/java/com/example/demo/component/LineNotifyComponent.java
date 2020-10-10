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
import com.example.demo.repository.DoramaRepository;

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
	
	
	public void executeNotification() {
		System.out.println("okkk");
		
		
		int StartweekOfDay;
		String message;
		
		List<Dorama> allDoramas = doramaRepository.findAll();
		
		
		Calendar today = Calendar.getInstance();
		
		for (Dorama dorama:allDoramas) {
			 StartweekOfDay = dorama.getStartDay().get(Calendar.DAY_OF_WEEK);
			 
			 //if (today.after(dorama.getStartDay()) && today.before(dorama.getEndDay()) && StartweekOfDay == today.get(Calendar.DAY_OF_WEEK)) {
					message = "本日"+dorama.getStartDay().get(Calendar.HOUR_OF_DAY)+"時から"+dorama.getName()+"が放送されます!";
					System.out.println("doramaName:"+dorama.getName());
					
					for (Favorite f :dorama.getFavorites()) {
						String token = f.getUser().getToken();
						
						if (token != null) {	
							System.out.println("tokenNamee:"+token);
							sendNotification(message,token);
						
						}
					}
			// }
		}
		
	}
		
		
	 public void sendNotification(String message, String token) {
	        HttpURLConnection connection = null;
	        try {
	        	System.out.println("a");
	            URL url = new URL("https://notify-api.line.me/api/notify");
	            System.out.println("b");
	            connection = (HttpURLConnection) url.openConnection();
	            System.out.println("c");
	            connection.setDoOutput(true);
	            System.out.println("d");
	            connection.setRequestMethod("POST");
	            System.out.println("e");
	            connection.addRequestProperty("Authorization", "Bearer " + token);
	            System.out.println("f");
	            try (OutputStream outputStream = connection.getOutputStream();
	                    PrintWriter writer = new PrintWriter(outputStream)) {
	            	System.out.println("g");
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
