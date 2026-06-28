package com.example.domainmodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class DomainmodelApplication {

	

	public static void main(String[] args) {

		//Automatically load variables from the .env file into the system properties
		Dotenv.configure().systemProperties().load();
		
		SpringApplication.run(DomainmodelApplication.class, args);
	}

}
