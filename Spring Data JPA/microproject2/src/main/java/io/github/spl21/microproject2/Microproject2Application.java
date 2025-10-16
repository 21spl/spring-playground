package io.github.spl21.microproject2;

import io.github.spl21.microproject2.entity.User;
import io.github.spl21.microproject2.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@SpringBootApplication
public class Microproject2Application {

	// setting a logger using slf4j
	private static final Logger log = LoggerFactory.getLogger(Microproject2Application.class);

	// main method
	public static void main(String[] args) {
		SpringApplication.run(Microproject2Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository repository)
	{
		return (args) -> {
			// save a few users
			repository.save(new User("Leo Messi", "messi@gmail.com"));
			repository.save(new User("Cristiano Ronaldo", "ronaldo@gmail.com"));
			repository.save(new User("Paulo Maldini", "maldini@gmail.com"));

			// fetch all users
			log.info("users found with findAll(): ");
			log.info("----------------------------------");
			repository.findAll().forEach(user -> {
				log.info(user.toString());
			});
			log.info("");
		};
	}
}
