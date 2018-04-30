package com.myretail.api.model;

import com.myretail.api.entity.Product;

public class ProductDetails {
	
	
	private int id;
	private String name;
	private Product priceDetails;
	
	public ProductDetails(){
	}

	public ProductDetails(int id,String name,Product priceDetails){
		this.id=id;
		this.name=name;
		this.priceDetails=priceDetails;
	}
	
	public Product getPriceDetails() {
		return priceDetails;
	}

	public void setPriceDetails(Product priceDetails) {
		this.priceDetails = priceDetails;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ProductDetails {"
				+ "id=" + id + ","
				+ "name=" + name +","
				+ priceDetails + 
				"}";
	}
}
