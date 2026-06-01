package com.tacos;

public class DefaultInventoryService implements InventoryService{

	@Override 
	public int getStock(String productId){
		return 42; // mimicking a db hit using a hardcoded value
	}

}
