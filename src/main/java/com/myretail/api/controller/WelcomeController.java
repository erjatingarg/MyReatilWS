package com.myretail.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class WelcomeController {
	@RequestMapping("/tcs")
	public String tcs() {
		return "Welcome to TCS";
	}
}