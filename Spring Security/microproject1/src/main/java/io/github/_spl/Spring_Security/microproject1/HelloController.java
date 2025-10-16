package io.github._spl.Spring_Security.microproject1;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String Hello()
    {
        return "hello!";
    }
}
