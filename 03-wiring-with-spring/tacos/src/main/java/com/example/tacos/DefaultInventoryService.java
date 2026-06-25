package com.example.tacos;

public class DefaultInventoryService implements InventoryService {
    
    @Override
    public int getStock(String productId){
        return 42;
    }
}
