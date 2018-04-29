package com.myretail.api.entity;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table("Product")
public class Product {

	@PrimaryKey("id")
	@Column(value = "id")
	private int productId;

	@Column(value = "price")
	private double value;
	
	@Column(value = "currency")
	private String currency_code;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public Product(int productId) {
		super();
		this.productId = productId;
	}

	public int getProductId() {
		return productId;
	}
}
