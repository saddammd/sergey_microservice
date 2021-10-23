package com.microservice.practical.demo.user.microservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name="User")
@Entity
public class RegisterUserEntity {

	@Id
	private String id;
	private String username;
	private String password;
	private String email;
	public RegisterUserEntity(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	@Override
	public String toString() {
		return "RegisterUserEntity [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ "]";
	}
	
	
	
	
	
	
}
