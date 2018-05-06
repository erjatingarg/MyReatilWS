package com.myretail.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.myretail.api.MyRetailApp;
import com.myretail.api.entity.Product;
import com.myretail.api.model.ProductDetails;
import com.myretail.api.repository.ProductRepository;
import com.myretail.api.service.ProductDetailsService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={MyRetailApp.class})
@SpringBootTest
public class ProductDetailsServiceImplTest {
	
	@Autowired
	private ProductDetailsService productDetailsService;
	
	@Autowired
	ProductRepository productRepository;
	
	
	private Product product;
	
	@Before
	public void setup() {
		createProduct();		
	    productRepository.save(product);
	}
	
	@Test
	public void testGetProductDetails() throws Exception {
		ProductDetails productDetails = productDetailsService.getProductDetails(product.getProductId());	
		assertEquals("79.87", productDetails.getPriceDetails().getValue().toString() );
	}
	

	@Test
	public void testPutProductDetails() throws Exception {
		ProductDetails productDetails = new ProductDetails();
		createProductDetails(productDetails );
	    productDetails = productDetailsService.putProductDetails(product.getProductId(),productDetails);
		assertEquals("100.0", productDetails.getPriceDetails().getValue().toString() );
	}
	
	@Test (expected=Exception.class)
	public void testGetProductDetailsNull() throws Exception{
		product.setProductId(100);
		ProductDetails productDetails = productDetailsService.getProductDetails(product.getProductId());	
		assertNull(productDetails);
	}
	
	private void createProductDetails( ProductDetails productDetails) {
		productDetails.setId(123);
		productDetails.setName("Apple IPhone");
		createProductNew() ;
		productDetails.setPriceDetails(product);
		productRepository.save(product);
	}
		
	private void createProductNew() {
	    product = new Product();
        product.setProductId(123);
        product.setCurrencyCode("USD");
        product.setValue(new Double(100.0));
    }
	
	private void createProduct() {
		product = new Product();
		product.setProductId(1234567);
		product.setCurrencyCode("USD");
		product.setValue(new Double(79.87));
	}
	
	@After
	public void delete() {		
		productRepository.delete(product);		
	}
}
