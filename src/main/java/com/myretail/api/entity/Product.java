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
	private Double value;
	
	@Column(value = "currency")
	private String currencyCode;

	public int getProductId() {
		return productId;
			
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
