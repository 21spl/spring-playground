package com.tacos;

public class ProductServiceTestSimulation {
    public static void main(String[] args) {
        System.out.println("=== RUNNING TEST SIMULATION ===");

        // Here is the pain point: 
        // Suppose We want ProductService to use our MockInventoryService (which returns 0 stock).
        // But ProductService instantly calls 'new InventoryService()' internally.
        
        ProductService productService = new ProductService();
        
        // There is no method like: productService.setInventoryService(mock);
        // There is no constructor like: new ProductService(mock);
        
        System.out.println("Expected Output from Mock: Stock should be 0");
        System.out.print("Actual Output: ");
        
        // This will print 'Stock: 42' because it is hardwired to the real service!
        productService.printProductInfo("ID-100"); 
        
        System.out.println("TEST FAILED: ProductService cannot be isolated from the real InventoryService.");
    }
}

