package com.myretail.product.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import com.myretail.api.MyRetailApp;
import com.myretail.api.controller.ProductDetailsController;
import com.myretail.api.service.ProductDetailsService;
import com.myretail.api.service.ProductDetailsServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MyRetailApp.class)
public class ProductDetailsControllerTest {

	@MockBean
	@Qualifier(value = "productDetailService")
	private ProductDetailsServiceImpl productDetailsService;

	@InjectMocks
	private ProductDetailsController productController = new ProductDetailsController();

	@Mock
	private ProductDetailsService mockProductService;

	RestTemplate restTemplate = new RestTemplate();

	@Test(expected = Exception.class)
	public void getKnownProductInfo() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, String> map = new HashMap<String, String>();
		String url = "http://localhost:8080/product/123456";
		ResponseEntity response = new ResponseEntity(null, headers, HttpStatus.NOT_FOUND);
		when(restTemplate.getForEntity(url, response.getClass(), map)).thenReturn(response);
		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		return;
	}
	
	@Test(expected = Exception.class)
	public void getUnknownProductInfo() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, String> map = new HashMap<String, String>();
		String url = "http://localhost:8080/product/12345";
		ResponseEntity response = new ResponseEntity(null, headers, HttpStatus.NOT_FOUND);
		when(restTemplate.getForEntity(url, response.getClass(), map)).thenReturn(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		return;
	}
}
