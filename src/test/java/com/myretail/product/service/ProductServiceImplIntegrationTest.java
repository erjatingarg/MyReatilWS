package com.myretail.product.service;


import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.myretail.api.MyReaillApp;
import com.myretail.api.entity.Product;
import com.myretail.api.model.ProductDetails;
import com.myretail.api.repository.ProductRepository;
import com.myretail.api.service.ProductDetailsService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={MyReaillApp.class})
@SpringBootTest
public class ProductServiceImplIntegrationTest {
	
	@Autowired
	private ProductDetailsService productDetailsService;
	
	@Autowired
	ProductRepository productRepository;
	
	@Value("${myRetail.product.hostname}")
	private String hostName;
	
	private Product product;
	
	@Before
	public void setup() {
		createProduct();		
	    productRepository.save(product);
	}
	
	@Test
	public void getProduct_Success() throws Exception {
		ProductDetails productDetails = productDetailsService.getProductDetails(product.getProductId());	
		assertEquals("79.87", productDetails.getPriceDetails().getValue().toString() );
	}
		
	private void createProduct() {
		product = new Product();
		product.setProductId(1234);
		product.setCurrencyCode("USD");
		product.setValue(new Double(79.87));
	}
	
	@After
	public void delete() {		
		productRepository.delete(product);		
	}
}
