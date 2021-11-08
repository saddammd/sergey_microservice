package com.microservice.practical.demo.user.microservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/config")
public class ConfServer {

	@Value("${name.user}")
	String name;
	
	@GetMapping("/name")
	public String getname() {
		
		return name;
	}
}
