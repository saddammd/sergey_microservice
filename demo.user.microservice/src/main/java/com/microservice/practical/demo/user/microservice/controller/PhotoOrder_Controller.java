package com.microservice.practical.demo.user.microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.practical.demo.user.microservice.model.Albums;

@RestController
public class PhotoOrder_Controller {
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/order/{userid}")
	public ResponseEntity<List> getOrder(@PathVariable String userid){
		
	String url = String.format("http://ALBUMS-WS/users/%s/albums", userid);
	
	ResponseEntity<List<Albums>> albumsResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Albums>>() {});
	List<Albums> body = albumsResponse.getBody();
	return new ResponseEntity<List>(body, HttpStatus.ACCEPTED);
	}
	
}
