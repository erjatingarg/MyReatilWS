package com.myretail.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.myretail.api.controller","com.myretail.api.model","com.myretail.api.service","com.myretail.api.repository" })

public class MyRetailApp{	    
	public static void main(String... args) {
		SpringApplication.run(MyRetailApp.class, args);
	}		
}


