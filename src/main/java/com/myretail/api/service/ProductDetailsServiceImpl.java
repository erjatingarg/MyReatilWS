package com.myretail.api.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.api.MyRetailUtil.TestUtils;
import com.myretail.api.entity.Product;
import com.myretail.api.model.PriceDetails;
import com.myretail.api.model.ProductDetails;
import com.myretail.api.repository.ProductRepository;


/**
 * Target Case Study
 * Author: Jatin Garg
 * version 1.0.0
 * Date :- 04/28/2018
 * Info :- Product detail service implementation 
 * */
@Service(value="productDetailService")
public class ProductDetailsServiceImpl implements ProductDetailsService {
	private final Logger log = Logger.getLogger(ProductDetailsServiceImpl.class.getName());
	private static final String PRODUCT_NOT_FOUND = "No product found for product id %s";
	
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
	public ProductDetails getProductDetails(int id) throws Exception,IOException{
		log.info("in  getProductDetails ");
		log.debug("id: "+id);
		String productName=getProductName(id);
		log.debug("productName: "+productName);
		Product prodPrice=productDetailsServiceImpl.getProductPrice(id);
		if(prodPrice==null){
			log.error("price detail null exception thrown");
			throw new Exception("price details for product with id="+id+" not found in Cassandra db");
		}
		ProductDetails prodDetails= new ProductDetails(id,productName,prodPrice);
		log.debug("prodDetails: "+prodDetails);
		return prodDetails;
	}

/*	@Override
	public ProductDetails putProductDetails(int id, ProductDetails newProduct) throws Exception{
		log.info("in putProductDetails");
		log.debug(" newProduct : "+newProduct);
		if(id!=newProduct.getId()){
			throw new Exception(" Product price cannot be updated, request body json should have matching id with path variable.");
		}
		PriceDetails newProductPrice=newProduct.getPriceDetails();
		if(newProduct.getProductPrice().getCurrencyCode()==null||newProduct.getProductPrice().getPrice()==null){
				throw new Exception(" Please check product price and currency code details, it should not be empty ");
		}
		newProductPrice.setId(id);
		String productName=getProductName(id);
		newProduct.setName(productName);
		newProductPrice=_productDetailsServiceImpl.updateProductPrice(id,newProduct);
		
		newProduct.setProductPrice(newProductPrice);
		return newProduct;
	}*/


	

	public Product getProductPrice(int id) throws Exception{
		log.info("in getProductPrice");
		log.debug("id : "+id);
		Product prodPrice=productRepository.findByProductId(id);
		log.debug("prodPrice : "+prodPrice);
		return prodPrice;
	}
	
	
/*	@CachePut(value = "productPriceCache", key = "#id")
	public ProductPrice updateProductPrice(int id,ProductDetails newProduct) throws Exception{
		log.info("in updateProductPrice");
		ProductPrice newProductPrice=newProduct.getProductPrice();
		newProductPrice.setId(id);
		if(productPriceRepository.findById(newProductPrice.getId())!=null){
			newProductPrice=productPriceRepository.save(newProduct.getProductPrice());
		}else{
			log.error("price detail null mongo exception thrown");
			throw new Exception("price details for product with id="+id+" not found in mongo db for collection 'productprice'");
		}
		log.debug("newProductPrice : "+newProductPrice);
		return newProductPrice;
	}*/
	
	private String getProductName(int id) throws IOException {
		log.info("in getProductName");
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
		} catch (Exception e) {
			log.error("Parsing failed IOException " + e.getMessage());
			throw new IOException(e.getMessage());
		}
		return productName;
	}

}
