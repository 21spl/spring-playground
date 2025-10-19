package io.github.spl21.spring.security.microproject2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig{

    @Bean
    UserDetailsService userDetailsService(){
        // builds the user with a given username, password and authorities list
        UserDetails user = User.withUsername("wrik")
                            .password(passwordEncoder().encode("12345"))
                            .authorities("read")
                            .build();

        // add the user to InMemoryUserDetailsManager
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
