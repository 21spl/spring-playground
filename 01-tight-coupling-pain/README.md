# Lab 01: The Pain of Tight Coupling

## The Code Flaw
Inside `ProductService.java`, the dependency is hardwired:
```java
private InventoryService inventoryService = new InventoryService();
```

## How to Verify the Pain
I wrote a simulation class `ProductServiceTestSimulation.java` trying to inject a `MockInventoryService` that returns `0` stock. 

Because of the `new` keyword, we have no entry point to pass our mock. Running the simulation outputs:
```text
=== RUNNING TEST SIMULATION ===
Expected Output from Mock: Stock should be 0
Actual Output: Product: ID-100 | Stock: 42
TEST FAILED: ProductService cannot be isolated from the real InventoryService.
```

## Conclusion
To test or change how inventory is managed, we are forced to open `ProductService.java` and modify its source code. This directly violates clean architecture and makes automated testing impossible.

