package com.example.demo;

public class Item{
    private Long id;
    private String name;
    private double price;


    // constructor
    public Item(Long id, String name, double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString(){
        return "ItemId = " + id + ", name = " + name + ", price = " + price;
    }

    
}