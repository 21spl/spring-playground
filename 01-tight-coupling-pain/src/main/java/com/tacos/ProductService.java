package com.tacos;


public class ProductService{

	// inventoryService is a field
	private InventoryService inventoryService = new InventoryService() ; // tight coupling
	
	public void printProductInfo(String productId){
		int stock = inventoryService.getStock(productId);
		System.out.println("Product: " + productId + " | Stock: " + stock);
	}
}
