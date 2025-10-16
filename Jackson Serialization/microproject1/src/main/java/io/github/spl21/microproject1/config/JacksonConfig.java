package io.github.spl21.microproject1.config;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class JacksonConfig {
    
    @Bean
    public ObjectMapper objectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();

        // configure to serialize JSON with pretty indenting
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        return objectMapper;
    }

}
