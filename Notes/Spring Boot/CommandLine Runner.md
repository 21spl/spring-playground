

###  CommandLineRunner
1. In spring boot, a class or bean that implements ```CommandLineRunner``` runs automatically after the application
2. context is loaded but before the application exits.

3. Here, we were creating a bean of type CommandLineRunner and run it at startup
   
4. The lambda ```(args) -> {...}``` is the implementation of ```CommandLineRunner.run(String... args)```.


5. Everything inside runs once when the app starts
   
6. It is typically used for:
    - Seeding initial data into the database
    - Running quick tests when the app starts
    - setting up defaults (Say setting up admin credentials initially and granting the role ADMIN)



```java

// we pass the reference of CustomerRepository - Repository interface and use save() method

@Bean
public CommandLineRunner demo(CustomerRepository repository)
{
    return (args) -> {
        // save a few customers
        repository.save(new Customer("Gigi", "Buffon"));
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
```

