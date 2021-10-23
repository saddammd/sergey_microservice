package com.microservice.practical.demo.user.microservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.microservice.practical.demo.user.microservice.model.LoginUser;
import com.microservice.practical.demo.user.microservice.model.RegisterUserEntity;
import com.microservice.practical.demo.user.microservice.repository.UserRepository;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	@Override
	public RegisterUserEntity Save(RegisterUserEntity user) {
		
		Optional<RegisterUserEntity> findById = userRepository.findById(user.getId());
		
		if(findById.isEmpty()) {
			user.setId(UUID.randomUUID().toString());
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);
					
			return user;
		}
		else {
			user.setPassword(encoder.encode(user.getPassword()));
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
	public RegisterUserEntity getUser(String id) {
		RegisterUserEntity registerUserEntity = userRepository.getById(id);
		
		
		
		return registerUserEntity;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<RegisterUserEntity> userEntity = userRepository.findByUsername(username);
		
		if(userEntity.isEmpty()) {
			throw new UsernameNotFoundException("Username" +username + " not found");
		}
		
		return new User(userEntity.get().getUsername(), userEntity.get().getPassword(), 
				true, true, true, true, new ArrayList<>());
	}

	@Override
	public Optional<RegisterUserEntity> getUserDetailsByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}

}
