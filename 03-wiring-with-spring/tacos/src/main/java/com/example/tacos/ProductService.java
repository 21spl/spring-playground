package com.example.tacos;

public class ProductService {

    private final InventoryService inventoryService;

    // dependency injection using constructor
    public ProductService(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    public void printProductInfo(String productId){
        int stock = inventoryService.getStock(productId);
        System.out.println("Product: " + productId + " | Stock: " + stock);
    }
    
}
