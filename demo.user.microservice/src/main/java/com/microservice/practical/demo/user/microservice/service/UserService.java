package com.microservice.practical.demo.user.microservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservice.practical.demo.user.microservice.model.RegisterUserEntity;

@Service
public interface UserService {

	public RegisterUserEntity Save(RegisterUserEntity user);
	public void delete(String id);
	public List<RegisterUserEntity> getUsers();
	public RegisterUserEntity get(String id);
}
