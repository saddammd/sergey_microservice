package com.microservice.practical.demo.user.microservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.practical.demo.user.microservice.model.RegisterUserEntity;

@Repository
public interface UserRepository extends JpaRepository<RegisterUserEntity, String> {

	public Optional<RegisterUserEntity> findById(String id);
	public Optional<RegisterUserEntity> findByUsername(String username);
}
