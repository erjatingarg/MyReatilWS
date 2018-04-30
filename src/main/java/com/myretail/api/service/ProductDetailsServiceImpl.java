package com.myretail.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.log4j.Logger;
import com.myretail.api.MyRetailUtil.TestUtils;
import com.myretail.api.entity.Product;
import com.myretail.api.model.ProductDetails;
import com.myretail.api.repository.ProductRepository;

/**
* Target Case Study
* Author: Jatin Garg
* version 1.0.0
* Date :- 04/28/2018
* Info :- Product detail service implementation 
* */

@Service(value = "productDetailService")
public class ProductDetailsServiceImpl implements ProductDetailsService {
	private final Logger log = Logger.getLogger(ProductDetailsServiceImpl.class.getName());

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductDetailsServiceImpl productDetailsServiceImpl;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Environment env;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public ProductDetails getProductDetails(int id) throws Exception, IOException {
		log.debug("id: " + id);
		String productName = getProductName(id);
		log.debug("productName: " + productName);
		Product prodPrice = productDetailsServiceImpl.getProductPrice(id);
		if (prodPrice == null) {
			log.error("price detail exception thrown");
			throw new Exception("price details for product with id=" + id + " not found in Cassandra db");
		}
		ProductDetails prodDetails = new ProductDetails(id, productName, prodPrice);
		log.debug("prodDetails: " + prodDetails);
		return prodDetails;
	}

	@Override
	public ProductDetails putProductDetails(int id, ProductDetails newProduct) throws Exception {
		log.debug("newProduct : " + newProduct);
		if (id != newProduct.getId()) {
			throw new Exception(
					" Product price cannot be updated, request body json should have matching id with path variable.");
		}
		Product newProductPrice = newProduct.getPriceDetails();
		if (newProduct.getPriceDetails().getCurrencyCode() == null
				|| newProduct.getPriceDetails().getValue() == null) {
			throw new Exception(" Please check product price and currency code details, it should not be empty.");
		}
		newProductPrice.setProductId(id);
		String productName = getProductName(id);
		newProduct.setName(productName);
		newProductPrice = productDetailsServiceImpl.updateProductPrice(id, newProduct);
		newProduct.setPriceDetails(newProductPrice);
		return newProduct;
	}

	public Product getProductPrice(int id) throws Exception {
		log.debug("id : " + id);
		Product prodPrice = productRepository.findByProductId(id);
		log.debug("prodPrice : " + prodPrice);
		return prodPrice;
	}

	public Product updateProductPrice(int id, ProductDetails newProduct) throws Exception {
		Product newProductPrice = newProduct.getPriceDetails();
		newProductPrice.setProductId(id);
		if (productRepository.findByProductId(newProductPrice.getProductId()) != null) {
			newProductPrice = productRepository.save(newProduct.getPriceDetails());
		} else {
			log.error("price detail null exception thrown");
			throw new Exception("price details for product with id=" + id + " not found in Cassandra db");
		}
		log.debug("newProductPrice : " + newProductPrice);
		return newProductPrice;
	}

	private String getProductName(int id) throws IOException {
		String url = env.getProperty("target.restUrl1") + id + env.getProperty("target.restUrl2");
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		ObjectMapper mapper = new ObjectMapper();
		String productName = "";
		try {
			JsonNode root = null;
			String jsonString = response.getBody();

			if (null == jsonString) {
				jsonString = TestUtils.getFileAsString("response.json");
			}

			root = mapper.readTree(jsonString);
			if (root.findValue("product") != null) {
				root = root.findValue("product");
				if (root.findValue("item") != null) {
					root = root.findValue("item");
					if (root.findValue("product_description") != null) {
						root = root.findValue("product_description");
						if (root.findValue("title") != null) {
							productName = root.findValue("title").asText();
						}
					}
				}
			}
			log.debug("productName : " + productName);
		} catch (IOException e) {
			log.error("Parsing failed IOException " + e.getMessage());
			throw new IOException(e.getMessage());
		}
		return productName;
	}
}
