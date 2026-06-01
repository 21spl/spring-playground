# 02 — Decoupling with Interface and Constructor Injection

## What This Lab Demonstrates

How to fix tight coupling **without any framework** — using a Java 
interface as a contract and constructor injection to hand in dependencies 
from outside.

## The Problem This Solves

In [Lab 01](../01-tight-coupling-pain), `ProductService` hardwired 
its dependency using `new InventoryService()` internally. This made 
the class impossible to unit test — there was no way to swap in a 
mock object.

## The Fix

Three things working together:

- **Interface** — defines the contract, not the implementation
- **Constructor injection** — dependency handed in from outside
- **Manual wiring** — caller decides which implementation to use

## File Structure
src/main/java/com/tacos/
├── InventoryService.java          # Interface — the contract
├── DefaultInventoryService.java   # Real implementation
├── MockInventoryService.java      # Fake implementation for testing
├── ProductService.java            # Depends on interface, not concrete class
└── Main.java                      # Manual wiring — production and test

## How to Run

```bash
cd 02-decoupling-with-interface
javac -d target/classes src/main/java/com/tacos/*.java
java -cp target/classes com.tacos.Main
```

## Expected Output
=== Using DefaultInventoryService ===
Product: ID-101 | Stock: 42
=== Using MockInventoryService ===
Product: ID-101 | Stock: 99

## Key Takeaway

Decoupling doesn't require Spring. It requires a design decision — 
program to an interface, not an implementation, and inject dependencies 
from outside rather than creating them inside.

Spring automates this wiring at scale. This lab shows what Spring 
is actually doing under the hood.

## Related

- [Lab 01 — Tight Coupling Pain](../01-tight-coupling-pain)
- [Blog Post](https://brokenbydesign.hashnode.dev) — full writeup 
  with explanation
