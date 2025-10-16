package io.github.spl21.microproject1;

import io.github.spl21.microproject1.entity.Customer;
import io.github.spl21.microproject1.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Microproject1Application {
	// setting up a logger using slf4j
	private static final Logger log = LoggerFactory.getLogger(Microproject1Application.class);

	// main method
	public static void main(String[] args) {
		SpringApplication.run(Microproject1Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository)
	{
		return (args) -> {
			// save a few customers
			repository.save(new Customer("Gigi", "Buffon"));
			repository.save(new Customer("Sergio", "Ramos"));
			repository.save(new Customer("Roy", "Keane"));
			repository.save(new Customer("Cristiano", "Ronaldo"));
			repository.save(new Customer("Leo", "Messi"));

			// fetch all customers
			log.info("customers found with findAll(): ");
			log.info("----------------------------------");
			repository.findAll().forEach(customer -> {
				log.info(customer.toString());
			});
			log.info("");

			// fetch customers by lastName
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			repository.findByLastName("Messi").forEach(Messi -> {
				log.info(Messi.toString());
			});
			log.info("");
		};
	}
}


/*
Note:
1. @SpringBootApplication annotation adds all the following annotations
	- @Configuration: Tags the class as a source of bean definition for ApplicationContext
	- @EnableAutoConfiguration: Tells spring boot to start adding beans on classpath settings
	- @ComponentScan: Tells spring boot to look for other components, configurations, services in the base package
2. main method uses ```SpringApplication.run()``` method to launch an application
3. To get the output (to the console), we need to set up a logger
4. Then we need to set up some data and use it to generate output
5. We can run the application by using: ```./mvnw spring-boot:run```
6. OR, we can build the JAR file with ```./mvnw clean package``` and then the jar file
	- ```java -jar target/microproject1.jar```



WHAT IS CommandLineRunner?

In spring boot, a class or bean that implements ```CommandLineRunner``` runs automatically after the application
context is loaded but before the application exits.

Here, we were creating a bean of type CommandLineRunner and run it at startup

The lambda (args) -> {...} is the implementation of CommandLineRunner.run(String... args).
Everything inside runs once when the app starts

It is typically used for:
- Seeding initial data into the database
- Running quick tests when the app starts
- setting up defaults (Say setting up admin credentials initially and granting the role ADMIN)

 */
