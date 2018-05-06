package com.myretail.api.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.myretail.api.MyRetailApp;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={MyRetailApp.class})
@SpringBootTest
public class ProductDetailsControllerTest {	
   
    @Test
	public void myRetailTest() {
        ProductDetailsController productDetailsController = new ProductDetailsController();
        String myRetail = productDetailsController.myRetail();
        Assert.assertTrue(myRetail.equals("Welcome to My Retail!")); 
    }
}
