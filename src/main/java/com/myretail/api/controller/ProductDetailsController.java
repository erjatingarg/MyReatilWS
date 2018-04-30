package com.myretail.api.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.myretail.api.model.ProductDetails;
import com.myretail.api.service.ProductDetailsService;
/**
 * Target Case Study
 * Author: Jatin Garg
 * version 1.0.0
 * Date :- 04/28/2018
 * Info :- Controller for myretail api
 * */
@RestController
public class ProductDetailsController {

	private final Logger log = Logger.getLogger(ProductDetailsController.class.getName());
	
	@Autowired
	ProductDetailsService productDetailsService;
	
	
	
	@RequestMapping("/")
	public String myRetail() {
		return "Welcome to My Retail!";
	}
	
	@RequestMapping(value="product/{id}",method=RequestMethod.GET)
	public ProductDetails getProductDetails(@PathVariable int id) throws Exception{
		log.info("in controller getProductDetails id :"+id);
		ProductDetails prodDetails=null;
		prodDetails=productDetailsService.getProductDetails(id);
		log.info(" return productDetails :"+prodDetails);
		return prodDetails;
	}
	
	@RequestMapping(value="product/{id}",method=RequestMethod.PUT)
	public ProductDetails putProductDetails(@PathVariable int id,@RequestBody ProductDetails prodDetails) throws Exception{
		log.info("in controller putProductDetails id :"+id);
		log.info("in controller putProductDetails requestBody :"+prodDetails);
		ProductDetails updatedProductDetails=productDetailsService.putProductDetails(id, prodDetails);
		log.info(" updated putProductDetails :"+updatedProductDetails);
		return updatedProductDetails;
	}

}
