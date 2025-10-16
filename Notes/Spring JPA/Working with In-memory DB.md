# Objective
**To build an application that stores ```Customer``` POJOs(Plain old java objects) in a 
memory-based database.**

This means:
- No external database (MySQL/Postgres/etc.) is connected.
- At startup, Spring Boot auto-creates an H2 database in RAM (```jdbc:h2:mem:...```).
- Our Customer entities get stored there.
- When we stop the app → the DB vanishes (all data gone).

## 1. Dependencies to be added in pom.xml

1. h2database
2. spring-boot-devtools
3. postgresql
4. projectlombok
5. spring-boot-starter-test


##  2. Creating an Entity class
1. Customer class has three attributes: id, firstName, lastName
2. **Default constructor exists only for the sake of JPA. We don't use it directly. So marked protected**
3. Customer class is annotated with @Entity, indicating that it is a JPA entity.
4. Because no @Table annotation exists, it is assumed that this entity is mapped to a table named Customer
5. id property is marked as @Id so that JPA recognizes it as object's ID (Primary Key)
6. @GeneratedValue(Strategy = GenerationType.AUTO) means, Id will be generated automatically

###### Customer.java

```java
@Data
@Getter
@Setter
@ToString
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    // no args constructor
    protected Customer(){}

    public Customer(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

```

## 3. Creating a CustomerRepository Interface 

1. Spring Data JPA focuses on using JPA to store data in a relational database
2. The most compelling feature is the ability to create repository interface automatically
   at runtime, from a repository interface
3. CustomerRepository extends the CrudRepository interface
4. The type of entity and ID that it works with are Customer and Long (datatype of customer entity id)
5. So CrudRepository  is specified by generic parameters<Customer, Long>
6. By extending CrudRepository, CustomerRepository inherits several methods for working with Customer persistence
7. Spring Data JPA also lets us define other query methods by declaring their method signature - findByLastName(), findById()
8. In typical java application, we write a class that implements CustomerRepository.
9. But it is the magic of Spring Data JPA which creates an implementation when we run the application


```java
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
}
```


## 4. Creating the Application class

1. This is the class with annotation ```@SpringBootApplication```
2. @SpringBootApplication annotation adds all the following annotations
    - @Configuration: Tags the class as a source of bean definition for ApplicationContext
    - @EnableAutoConfiguration: Tells spring boot to start adding beans on classpath settings
    - @ComponentScan: Tells spring boot to look for other components, configurations, services in the base package
3. main method uses ```SpringApplication.run()``` method to launch an application
4. To get the output (to the console), we need to set up a logger
5. Then we need to set up some data and use it to generate output
6. We can run the application by using: ```./mvnw spring-boot:run```
7. OR, we can build the JAR file with ```./mvnw clean package``` and then the jar file
    - ```java -jar target/microproject1.jar```
```java
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
```



###  5. CommandLineRunner
In spring boot, a class or bean that implements ```CommandLineRunner``` runs automatically after the application
context is loaded but before the application exits.

Here, we were creating a bean of type CommandLineRunner and run it at startup

The lambda (args) -> {...} is the implementation of CommandLineRunner.run(String... args).
Everything inside runs once when the app starts

It is typically used for:
- Seeding initial data into the database
- Running quick tests when the app starts
- setting up defaults (Say setting up admin credentials initially and granting the role ADMIN)


