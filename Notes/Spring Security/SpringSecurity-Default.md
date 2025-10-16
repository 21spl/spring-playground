# Default Behaviour of Spring Security

### Step 1: Add Spring Security Dependency in Maven

We need to add Spring Security to our application's classpath. There are two ways to do this:
- Using Maven
- Using Gradle
  
### Step 2: Running the application

With Spring Security on the classpath, we can run the application.

On running the application, we get the following in terminal:

```maven

$ ./mvnw spring-boot:run
...
INFO 23689 --- [  restartedMain] .s.s.UserDetailsServiceAutoConfiguration :

Using generated security password: 8e557245-73e2-4286-969a-ff57fe326336

...
```

Now that we have it running, if we try to hit an endpoint, we get the following output:

```bash
$ curl -i http://localhost:8080/some/path
HTTP/1.1 401
...
```

then Spring Security denies access with a ```401 Unauthorised```

**TIP**: If we provide the same URL in a browser, it will redirect to a default login page. (This login page is provided by Spring)


Now, if we hit an endpoint with credentials (given in the terminal), we get follows:

```bash
$ curl -i -u user:8e557245-73e2-4286-969a-ff57fe326336 http://localhost:8080/some/path
HTTP/1.1 404
...
```

## Understanding default behaviour of Spring Security


The default arrangement of Spring Boot and Spring Security affords the following behaviors at runtime:

- Requires an authenticated user for any endpoint (including Boot’s /error endpoint)

- Registers a default user with a generated password at startup (the password is logged to the console; in the preceding example, the password is 8e557245-73e2-4286-969a-ff57fe326336)

- Protects password storage with BCrypt as well as others

- Provides form-based login and logout flows

- Authenticates form-based login as well as HTTP Basic

- Provides content negotiation; for web requests, redirects to the login page; for service requests, returns a 401 Unauthorized

- Mitigates CSRF attacks

- Mitigates Session Fixation attacks

- Writes Strict-Transport-Security to ensure HTTPS

- Writes X-Content-Type-Options to mitigate sniffing attacks

- Writes Cache Control headers that protect authenticated resources

- Writes X-Frame-Options to mitigate Clickjacking

- Integrates with HttpServletRequest's authentication methods

- Publishes authentication success and failure events



### Understanding Spring Security's auto-configuration

It can be helpful to understand how Spring Boot is coordinating with Spring Security to achieve this. Taking a look at Boot’s security auto configuration, it does the following (simplified for illustration):


```java
@EnableWebSecurity 
@Configuration
public class DefaultSecurityConfig {
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    InMemoryUserDetailsManager inMemoryUserDetailsManager() { 
        String generatedPassword = // ...;
        return new InMemoryUserDetailsManager(User.withUsername("user")
                .password(generatedPassword).roles("USER").build());
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEventPublisher.class)
    DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher delegate) { 
        return new DefaultAuthenticationEventPublisher(delegate);
    }
}
```
- Adds the @EnableWebSecurity annotation. (Among other things, this publishes Spring Security’s default Filter chain as a @Bean)

- Publishes a UserDetailsService @Bean with a username of user and a randomly generated password that is logged to the console
  
- Publishes an AuthenticationEventPublisher @Bean for publishing authentication events

- Spring Boot adds any Filter published as a @Bean to the application’s filter chain. This means that using @EnableWebSecurity in conjunction with Spring Boot automatically registers Spring Security’s filter chain for every request.