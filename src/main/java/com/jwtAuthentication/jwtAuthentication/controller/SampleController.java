package com.jwtAuthentication.jwtAuthentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getString")
public class SampleController {
	
	@GetMapping
	public String getResource() {
		System.out.println("Hello String");
		return "Hello String";
	}

}
