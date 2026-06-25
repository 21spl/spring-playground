package com.example.tacos;

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.*;


public class Main{

	public static void main(String[] args){
		// Bootstrap the Spring IoC container
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Ask the container for the bean
		ProductService productService = context.getBean(ProductService.class);
		productService.printProductInfo("SKU-101");

	}

}