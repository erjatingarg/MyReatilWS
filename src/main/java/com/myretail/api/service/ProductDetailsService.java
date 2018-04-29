package com.myretail.api.service;

import com.myretail.api.model.ProductDetails;

/**
 * Target design challenge option 1
 * Author: Jatin Garg
 * version 1.0.0
 * Date :- 04/28/2018
 * Info :- Product detail service interface
 * */
public interface ProductDetailsService {

	public ProductDetails getProductDetails(int id) throws Exception;
	/*public ProductDetails putProductDetails(int id,ProductDetails newProduct) throws Exception;*/
}
