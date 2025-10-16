/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package io.github.spl21.microproject3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 21pau
 */

 @RestController
public class ApiController {

    @Value("${app.api.key}")
    private String apiKey;

    @GetMapping("/api")
    public String api(){
        return apiKey;
    }
    
}
