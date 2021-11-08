package com.microservice.practical.demo.user.microservice.controller;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.practical.demo.user.microservice.model.RegisterUser;
import com.microservice.practical.demo.user.microservice.model.RegisterUserEntity;
import com.microservice.practical.demo.user.microservice.service.UserService;

@RestController
public class User_Controller {

	@Autowired
	public UserService userService;
	
	
	@PostMapping(value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
									  consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<RegisterUser> registerUser(@RequestBody RegisterUser register_user) {
				
		RegisterUserEntity userEntity = new RegisterUserEntity();
		BeanUtils.copyProperties(register_user, userEntity);
		userService.Save(userEntity);		
		return new ResponseEntity<RegisterUser>(register_user, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<RegisterUserEntity> viewUsers () {
		
		System.out.println(userService.getUsers());
		return userService.getUsers();
		}
	
	
	@PutMapping(value = "/users", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public RegisterUserEntity update(@RequestBody RegisterUser registerUser) {
		
		RegisterUserEntity user = new RegisterUserEntity();
		
		if(userService.getUser(registerUser.getId())!=null) {
			
			BeanUtils.copyProperties(registerUser, user);
			
			RegisterUserEntity updated_user = userService.Save(user);
		
			return updated_user;
		}
		else throw new IllegalArgumentException(registerUser.getId() +" id is not found");
		
		
	}
	@PreAuthorize("principal == #id")
	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
		
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
		}
}