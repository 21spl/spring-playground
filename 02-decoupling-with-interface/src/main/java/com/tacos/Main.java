package com.tacos;

public class Main {

	public static void main(String[] args){
	
		System.out.println("=== Using DefaultInventoryService ===");
		// production wiring - manual wiring - no autowiring since we are not using spring
		InventoryService real = new DefaultInventoryService();
		ProductService productService = new ProductService(real); // passing the object of dependency class
		productService.printProductInfo("ID-101");
		
		
		
		System.out.println("=== Using MockInventoryService ===");
		// Test wiring - manual wiring - no autowiring since we are not using spring
		InventoryService mock = new MockInventoryService();
		ProductService testService = new ProductService(mock);
		testService.printProductInfo("ID-101");
	
	}

}
