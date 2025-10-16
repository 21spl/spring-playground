package io.github._spl.Spring_Security.microproject1;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CustomSecurityConfig {


    // Bean 1: Bean for UserDetailsService
    @Bean
    UserDetailsService userDetailsService(){

        UserDetails customUser = User.withUsername("wrik")
                .password("12345")
                .authorities("read")
                .build();
        // InMemoryUserDetailsManager is an implementation of UserDetailsService Interface
        return new InMemoryUserDetailsManager(customUser);
    }

    // Bean 2: Bean for passwordEncoder
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    // Bean 3: Bean for SecurityFilterChain
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

    // Bean 4: Bean for registering our customAuthenticationProvider
    private final CustomAuthenticationProvider authenticationProvider;

    // contructor injection
    // Note, in CustomAuthenticationProvider class, we used @component annotation
    public CustomSecurityConfig(CustomAuthenticationProvider authenticationProvider){
        this.authenticationProvider = authenticationProvider;
    }
}

