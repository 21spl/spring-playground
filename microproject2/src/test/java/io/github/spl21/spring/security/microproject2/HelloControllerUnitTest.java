package io.github.spl21.spring.security.microproject2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HelloControllerUnitTest {

    @Test
    void sayHelloReturnsMessage() {
        HelloController helloController = new HelloController();
        String response = helloController.sayHello();
        assertEquals("Hello, World!", response); // proper JUnit assertion
    }
}

