package com.podkaster.aiservice.chatservice;


import com.podkaster.aiservice.chatservice.openai.service.SongRecommendService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(SongRecommendService aiservice) {
		return runner -> {
			System.out.println("Hello Chat service");
			String userText = """
    Users Song History{ 
    	"Bohemian Rhapsody - Queen,",
		"Imagine - John Lennon",
		"Hotel California - Eagles",
		"Stairway to Heaven - Led Zeppelin",
		"Smells Like Teen Spirit - Nirvana"
    }
    """;
			String result = aiservice.processRequestWithJson(userText, "gpt-3.5-turbo");
			System.out.println("Response: %s".formatted(result));
		};
	}
}
