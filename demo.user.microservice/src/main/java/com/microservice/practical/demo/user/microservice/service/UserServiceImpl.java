package com.microservice.practical.demo.user.microservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.microservice.practical.demo.user.microservice.model.RegisterUserEntity;
import com.microservice.practical.demo.user.microservice.repository.UserRepository;

public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public RegisterUserEntity Save(RegisterUserEntity user) {
		
		Optional<RegisterUserEntity> findById = userRepository.findById(user.getId());
		
		if(findById.isEmpty()) {
			user.setId(UUID.randomUUID().toString());
			userRepository.save(user);
			
			return user;
		}
		else {
			
			userRepository.save(user);
		}
		
		return user;
	}

	@Override
	public void delete(String id) {
		if(userRepository.getById(id)!=null) {
			userRepository.deleteById(id);
		}
		else throw new IllegalArgumentException(id +"not found");

	}

	@Override
	public List<RegisterUserEntity> getUsers() {
		
		return userRepository.findAll();
		
	}

	@Override
	public RegisterUserEntity get(String id) {
		RegisterUserEntity registerUserEntity = userRepository.getById(id);
		return registerUserEntity;
	}

}
