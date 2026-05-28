package com.tacos;

// we want to use this fake class to test what happens when the stock is empty
public class MockInventoryService extends InventoryService{

	@Override
	public int getStock(String productId){
		return 0; // return empty stock for testing purposes
	}

}
