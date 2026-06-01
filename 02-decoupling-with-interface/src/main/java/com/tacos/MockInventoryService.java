package com.tacos;

public class MockInventoryService implements InventoryService{

	@Override
	public int getStock(String productId){
		return 99; // using a fake data for testing
	}

}
