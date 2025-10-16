# Username/Password Authentication - only for testing

#### When to use?
1. When you are building a demo app or a proof-of-concept, and you don’t want to set up a database or a full user management system yet.

2. Quick way to test Spring Security flows (login form, HTTP Basic, role-based authorization).



###### Create a Separate config class
```java

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests((authorize) -> authorize
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
        return http.build();
    }

    @Bean UserDetailsService userDetailsService(){
        UserDetails userDetails = User.WithDefaultPasswordEncoder()
        .username("admin")
        .password("secret")
        .roles("USER")
        .build();

        return new InMemoryUserDetailsManager(userDetails);
    }
    
}

```



