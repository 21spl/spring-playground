package com.example.tacos;
// remember we added the dependency with group name - org.springframework
// and artifact Id - spring-context
// now we are using annotations, so the import looks like:
import org.springframework.context.annotation.*;

@Configuration
public class AppConfig{

    @Bean
    public InventoryService inventoryService(){
        return new DefaultInventoryService();
        // swap to MockInventoryService if you want to test
    }

    @Bean
    public ProductService productService(){
        return new ProductService(inventoryService());
    }
}
