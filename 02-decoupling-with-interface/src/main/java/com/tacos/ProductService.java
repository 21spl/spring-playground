package com.tacos;

public class ProductService {

	private final InventoryService inventoryService;
	
	// Dependency injected via constructor
	public ProductService(InventoryService inventoryService){
		this.inventoryService = inventoryService;
	}
	
	public void printProductInfo(String productId){
		int stock = inventoryService.getStock(productId);
		System.out.println("Product: " + productId + " | Stock: " + stock);
	}
	
}
