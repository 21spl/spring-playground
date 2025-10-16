# Creating a Custom Filter Chain

##### checklist:
1. This customFilterChain Must be inside a config class (```@Configuration```)
2. the class must have ```@EnableWebConfiguration```  annotation
3. The filter chain must be declared a Bean


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
    
    return http.build()
}

```

Notes:
1. Ordering of filters is important
2. Customizer is a functional interface
3. Customizer.withDefaults() applies default configuration for that feature
4. authorizeHttpRequests() method defines authorization rules (not authentication rules)
5. Security filters are declared using an ```HttpSecurity``` instance


### 1.1 To know proper filter ordering:

Check [FilterOrderRegistrationCode](https://github.com/spring-projects/spring-security/tree/6.5.3/config/src/main/java/org/springframework/security/config/annotation/web/builders/FilterOrderRegistration.java)

### 1.2 Creating Custom Authorization Rules


```java
authorizeHttpRequests(auth -> auth
    .requestMatchers("/admin/**").hasRole("ADMIN")
    .requestMatchers("/user/**").hasRole("USER")
    .requestMatchers("/public/**").permitAll()
    .anyRequest().authenticated()
)
```


### 1.3 Printing the Security Filters

The list of filters is printed at DEBUG level on the application startup, so you can see something like the following on the console output for example:

```text
2023-06-14T08:55:22.321-03:00  DEBUG 76975 --- [           main] o.s.s.web.DefaultSecurityFilterChain     : Will secure any request with [ DisableEncodeUrlFilter, WebAsyncManagerIntegrationFilter, SecurityContextHolderFilter, HeaderWriterFilter, CsrfFilter, LogoutFilter, UsernamePasswordAuthenticationFilter, DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter, BasicAuthenticationFilter, RequestCacheAwareFilter, SecurityContextHolderAwareRequestFilter, AnonymousAuthenticationFilter, ExceptionTranslationFilter, AuthorizationFilter]
```


### 1.4 Creating Custom Filters:

[Check documentation](https://docs.spring.io/spring-security/reference/servlet/architecture.html#adding-custom-filter)







### Futher Discussion

#### 1. Customizer Functional Interface

``` Java
@FunctionalInterface
public Interface Customizer<T>()
{
    // abstract method
    void customize(T t);
    // static method
    static <T> Customizer<T> withDefaults(){
        return (t) -> {};
    }
}
```