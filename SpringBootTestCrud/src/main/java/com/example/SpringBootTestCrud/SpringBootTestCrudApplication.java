package com.example.SpringBootTestCrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTestCrudApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringBootTestCrudApplication.class);
		app.setAdditionalProfiles("development");
		app.run(args);
	}
}


