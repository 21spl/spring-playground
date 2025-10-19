package io.github.spl21.spring.security.microproject2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserDetailsServiceTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testLoadUserByUsername(){
        UserDetails userdetails = userDetailsService.loadUserByUsername("wrik");
        assertEquals("wrik", userdetails.getUsername());
        assertTrue(passwordEncoder.matches("12345", userdetails.getPassword()));
        assertTrue(userdetails.getAuthorities().stream()
                   .anyMatch(auth -> auth.getAuthority().equals("read")));
    }

}
