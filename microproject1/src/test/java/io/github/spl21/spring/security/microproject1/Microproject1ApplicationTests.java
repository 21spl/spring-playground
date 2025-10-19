package io.github.spl21.spring.security.microproject1;

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
class Microproject1ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldFailWithoutAuth() throws Exception{
		this.mockMvc.perform(get("/hello"))
			.andExpect(status().isUnauthorized());
	}


	@Test
	void shouldSucceedWithAuth() throws Exception{
		this.mockMvc.perform(get("/hello").with(httpBasic("User",
				"9966a333-d260-4b91-b518-cf66f5eb1f37")))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, World!"));
				
	}
}