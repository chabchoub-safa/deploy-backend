package com.example.PFE;

import com.mongodb.client.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling


		()
@SpringBootApplication
public class PfeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfeApplication.class, args);
	}
	@Bean
	CommandLineRunner showMongo(@Value("${spring.data.mongodb.uri:NOT_SET}") String uri) {
		return args -> System.out.println("✅ spring.data.mongodb.uri = " + uri);
	}
	@Bean
	CommandLineRunner logMongoInfo(MongoClient mongoClient) {
		return args -> {
			System.out.println("🔎 ===== MONGO CONNECTION INFO =====");
			System.out.println("MongoClient class = " + mongoClient.getClass().getName());
			System.out.println("MongoClient = " + mongoClient);
			System.out.println("===================================");
		};
	}
}
