# Project Goal

1. At first, we want to see the behaviour of our application without adding spring security in dependencies
2. Next, we will add spring security in dependencies to learn what changes
3. Then, we know what we can expect from the default configuration for authentication and authorization
4. Then we change the project to add functionality for user management by overriding the defaults to define
custom users and passwords
5. Apply different styles for the same configurations to understand best practices

# Part 1. Without applying Spring Security

### Step 1: Create a Controller class and a REST endpoint

```java
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String Hello()
    {
        return "hello!";
    }
}
```

Now run the application. The application is running on port: 8080.
So open the browser and open the link: http://localhost:8080/hello
we will find a "hello!" written on the page

# Part 2: Adding spring-security dependency in pom.xml to get the default security

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
1. Now when we try to access the URL through browser, we get the login form with username and password
2. Now, if we try to access the endpoint: http://localhost:8080/hello via postman, 
we get:

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Unauthorized",
  "path": "/hello"
}
```

3. Now observe carefully, in the terminal, the following line:

```txt
Using generated security password: ca4a6e94-416e-4c84-90bd-60d74d4d68dc
This generated password is for development use only. Your security configuration must be updated before running your application in production.
```
4. Now, in the login form if we pass "user" as username and the above password as password,
we will be able to reach the endpoint
5. Similarly, if we pass this username and password as authentication headers (Basic Authentication)
in postman and then hit the end point with GET request, we will be able to reach.


### How Spring Security Works?




### Note: Understanding important HTTP code

1. HTTP 401 Unauthorized access is ambiguous
2. Usually it is used to represent a failed authentication not failed authorization
3. So we will use 403 Forbidden for failed authorization- This means server
has identified the caller of the request, but they don't have the privilege for the call
they are trying to make.
4. HTTP 401 is used to represent failed authentication

# Part 3: Overriding default Configuration - Method 1

## Step 1: Customizing User details management

- Here we will define a custom bean of type UserDetailsService
- Here we will not write our own implementation of UserDetailsService
- We will use an implementation provided by spring security known as: ```InMemoryUserDetailsManager```
- This implementation stores the credentials in memory and can be used by spring for authentication
- We will create a configuration class, where we store the bean
- Inside the bean, we have to create atleast one user who has a set of credentials (username and password)
- This user will be managed by our implementation of UserDetailsService, i.e ```InMemoryUserDetailsManager```
- also define a bean of type ```PasswordEncoder``` that can be used to verify a given password with the one stored and
managed by ```InMemoryUserDetailsService```

```java
@Configuration
public class CustomConfig {
    @Bean
    UserDetailsService userDetailsService() {
        UserDetails customUser = User.withUsername("wrik")
                .password("12345")
                .authorities("read")
                .build();
        // we are passing customUser inside the constructor of InMemoryUserDetailsManager
        // so that this user can be managed by UserDetailsService
        return new InMemoryUserDetailsManager(customUser);
    }
}

```

##### Note:
1. This ```User``` datatype is not any entity class we have defined
2. This class is provided by Spring Security to represent an user
3. Note: Declaring just the UserDetailsService bean is not enough to reach the endpoint
4. If we call the endpoint, we get the following error message:

```text
IllegalArgumentException: There is no PasswordEncoder mapped ...
```

5. Therefore, we must declare a bean for PasswordEncoder in the same configuration class

```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

## Step 2: Applying Authorization at the endpoint level

1. With default configuration, all the endpoints assume we have a valid user managed by the application
2. But, not all endpoints of an application need to be secured.
3. Therefore, for different endpoints, we need different authentication methods and authorization rules
4. To Customize the handling of authentication and authorization, we will define a bean of type
```SecurityFilterChain```

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

    http.
        csrf(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults())
        .authorizeHttpRequests(authorize -> authorize
        .anyRequest().authenticated()
        );
    
    return http.build();
}
```

### Customizing authorization rules 
To customize authorization rules, we can modify authorizeHttpRequests() method as follows:

```java

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.
            csrf(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/user/**").hasRole("USER")
                    .requestMatchers("/public/**").permitAll()
                    .anyRequest().authenticated()
            );

    return http.build();
}

```


Note: 
1. Above snippet uses Customizer interface. ```Customizer``` in a functional interface
2. Thus lambda expression can be used to implement the Customizer functional interface

```java
import org.springframework.boot.web.codec.CodecCustomizer;

@FunctionalInterface
public interface Customizer<T> {
    void customize(T t);

    static <T>CodecCustomizer<T> withDefaults(){
        return (t) -> {};
    }
}
```



# Part 4: Defining Custom Authentication logic - ```AuthenticationProvider```

### Step 1: Implementing ```AuthenticationProvider``` interface

1. The ```AuthenticationProvider``` implements the authentication logic.
2. It receives the request from ```AuthenticationManager``` and delegates finding the user to a 
```UserDetailsService```, verifying the password to a ```PasswordEncoder```
3. Here, our ```CustomAuthenticationProvider``` is treated as a bean using ```@Component``` annotation.

```java
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // entire authentication logic here
        String Username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        if ("wrik".equals(username) && "12345".equals(password)) {
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    Arrays.asList()
            );
        } else {
            throw new AuthenticationException("Wrong Credentials");
        }
        // the above if-else condition replaces UserDetailsService and PasswordEncoder bean
        // But if we work with other users and use passwordEncoder, then we must keep these.
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        // type of authentication implemented here
        return UsernamePasswordAuthenticationToken
            .class
                .isAssignableFrom(authenticationType);
    }
}
```

### Step 2: Registering the custom ```AuthenticationProvider``` in configuration class

1. Now in the configuration class, we can register the ```AuthenticationProvider``` using the 
HttpSecurity authenticationProvider() method shown as follows

```java
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig{
    
    private final CustomAuthenticationProvider authenticationProvider;
    
    // contructor injection
    // Note, in CustomAuthenticationProvider class, we used @component annotation
    public CustomConfig(CustomAuthenticationProvider authenticationProvider){
        this.authenticationProvider = authenticationProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.
                csrf(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails customUser = User.withUsername("wrik")
                .password("12345")
                .authorities("read")
                .build();
        // we are passing customUser inside the constructor of InMemoryUserDetailsManager
        // so that this user can be managed by UserDetailsService
        return new InMemoryUserDetailsManager(customUser);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

# Part 5: Finally

Now we can call the endpoint http://localhost:8080/hello, which is accessible by the only user "wrik" with 
password "12345".


# Part 6: Moving Forward

## 1. Using multiple configuration classes

- sometimes it is a good practice to have more than one configuration class to make the project readable

###### 1.1 Configuration class for UserManagement
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserManagementConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = new InMemoryUserDetailsManager();

        UserDetails user = User.withUsername("wrik")
                .password("12345")
                .authorities("read")
                .build();
        
        userDetailsService.createUser(user);
        
        return userDetailsService;
        
    }
}
```

###### 1.2 Configuration class for Authorization

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebAuthorizationConfig {

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        
        // allows all authenticated requests
        http.authorizeHttpRequests(
                c -> c.anyRequest().authenticated()
        );
        
        return http.build();
    }
}
```

