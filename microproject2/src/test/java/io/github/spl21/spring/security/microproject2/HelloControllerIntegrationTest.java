package io.github.spl21.spring.security.microproject2;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloEndPointRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/hello"))
               .andExpect(status().isUnauthorized());
    }

    @Test 
    void helloEndpointWithValidCredentials() throws Exception{
        mockMvc.perform(get("/hello")
               .with(httpBasic("wrik", "12345")))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello, World!"));
    }

    @Test
    void helloEndpointWithInvalidUserFails() throws Exception {
        mockMvc.perform(get("/hello")
                .with(httpBasic("wrong", "password")))
                .andExpect(status().isUnauthorized());
    }

}
