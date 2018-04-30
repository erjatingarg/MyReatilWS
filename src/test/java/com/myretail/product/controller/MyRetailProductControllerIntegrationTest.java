package com.myretail.product.controller;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.myretail.api.model.ProductDetails;
import com.myretail.api.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class MyRetailProductControllerIntegrationTest {

	@LocalServerPort
	private int port ;

	private TestRestTemplate template = new TestRestTemplate();

	@Test
	public void testGetProduct() {
		ResponseEntity<ProductDetails> productEntity = this.template.getForEntity("http://localhost:"
				+ port +"/myretail/products/{productId}", ProductDetails.class, 13860428);
		assertEquals("The Big Lebowski (Blu-ray) (Widescreen)", productEntity.getBody().getName());
		assertEquals(HttpStatus.OK, productEntity.getStatusCode());
	}
	
	@Test
	public void testGetProductNotFound() {
		ResponseEntity<ProductDetails> productEntity = this.template.getForEntity("http://localhost:"
				+ port +"/myretail/products/{productId}", ProductDetails.class, 138604281);
		assertEquals(HttpStatus.NOT_FOUND, productEntity.getStatusCode());
	}
	
	@Test
	public void testGetProductForbidden() {
		ResponseEntity<ProductDetails> productEntity = this.template.getForEntity("http://localhost:"
				+ port +"/myretail/products/{productId}", ProductDetails.class, 16483589);
		assertEquals(HttpStatus.FORBIDDEN, productEntity.getStatusCode());
	}

	@Test
	public void testUpdateProductPrice_ProductNotFound() {
		ProductDetails productDetails = new ProductDetails();
		Product product = new Product();
		product.setValue(99.99);
		product.setProductId(1234);
		productDetails.setPriceDetails(product);	
	    HttpEntity<ProductDetails> requestEntity = new HttpEntity<ProductDetails>(productDetails);
	    ResponseEntity<String> responseEntity = 
	    		this.template.exchange("http://localhost:"
	    				+ port +"/myretail/products/{productId}", HttpMethod.PUT, requestEntity, String.class, 1234);
	    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdateProductPrice() {
		ProductDetails productDetails = new ProductDetails();
		Product product = new Product();
		product.setValue(99.99);
		product.setProductId(1234);
		productDetails.setPriceDetails(product);
	    HttpEntity<ProductDetails> requestEntity = new HttpEntity<ProductDetails>(productDetails);
	    ResponseEntity<String> responseEntity = 
	    		this.template.exchange("http://localhost:"
	    				+ port +"/myretail/products/{productId}", HttpMethod.PUT, requestEntity, String.class, 13860428);
	    assertEquals(HttpStatus.RESET_CONTENT, responseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdateProductPrice_BadRequest() {
		ProductDetails productDetails = new ProductDetails();
		Product product = new Product();
		product.setValue(99.99);
		product.setProductId(1234);
		productDetails.setPriceDetails(product);
		HttpEntity<ProductDetails> requestEntity = new HttpEntity<ProductDetails>(productDetails);
	    ResponseEntity<String> responseEntity = 
	    		this.template.exchange("http://localhost:"
	    				+ port +"/myretail/products/{productId}", HttpMethod.PUT, requestEntity, String.class, 0);
	    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
}
