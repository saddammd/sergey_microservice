package com.microservice.practical.demo.user.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegisterUser {

	private String id;
	private String username;
	private String password;
	private String email;
}
