package com.microservice.practical.demo.user.microservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.microservice.practical.demo.user.microservice.model.RegisterUserEntity;

@Service
public interface UserService extends UserDetailsService {

	public RegisterUserEntity Save(RegisterUserEntity user);
	public void delete(String id);
	public List<RegisterUserEntity> getUsers();
	public RegisterUserEntity getUser(String id);
	public Optional<RegisterUserEntity> getUserDetailsByUsername(String username);
	
}
