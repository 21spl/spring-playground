# Creating the licensing Service

### 1. Required Dependencies
1. spring boot web
2. Spring actuator
3. lombok
4. spring boot dev tools

### 2. File Structure of ```licensing-service```
1. A spring bootstrap class - main class/application class
2. Controller class

Bootstrap class - used by Spring Boot to start up and initialize the application
Controller class - exposes the HTTP endpoints that can be invoked on the microservices

### 3. Bootstrap class

1. @SpringBootApplication - annotation tells the Spring Boot framework that this is the
project's bootstrap class
2. SpringApplication.run() - method starts the entire Spring Boot application
3. @SpringBootApplication - annotation tells the Spring container that this class is the source of bean definition'
4. We can also define Spring Beans by annotation a class with - ```@Component```, ```@Service``` and ```@Respository```
5. We can also define Spring beans by ```@Configuration``` tag and then defining a factory method for each Spring bean we want to 
build with a ```@Bean``` tag.
6. A spring bean is an object that the Spring framework manages at runtime with IoC (Inversion of Control) container
7. the ```SpringApplication.run()``` method inside the main() method in the bootstrap class *starts 
the Spring container* and returns a spring ```ApplicationContext``` object.
