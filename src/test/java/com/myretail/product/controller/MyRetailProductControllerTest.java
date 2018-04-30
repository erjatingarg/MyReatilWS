package com.myretail.product.controller;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import com.myretail.api.MyReaillApp;
import com.myretail.api.controller.ProductDetailsController;
import com.myretail.api.exception.ProductNotFoundException;
import com.myretail.api.service.ProductDetailsService;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes={MyReaillApp.class})
public class MyRetailProductControllerTest {
	
	@InjectMocks
	private ProductDetailsController productController = new ProductDetailsController();
    
    @Mock
    private ProductDetailsService mockProductService;
    	
	@Test(expected=ProductNotFoundException.class)
	public void getProduct_NotFound() throws Exception {		
		when(mockProductService.getProductDetails(anyInt()))
	    .thenThrow(new ProductNotFoundException());
		productController.getProductDetails(123);
	}
}
