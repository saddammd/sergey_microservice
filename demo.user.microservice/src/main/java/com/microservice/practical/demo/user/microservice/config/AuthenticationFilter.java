package com.microservice.practical.demo.user.microservice.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.practical.demo.user.microservice.model.LoginUser;
import com.microservice.practical.demo.user.microservice.model.RegisterUserEntity;
import com.microservice.practical.demo.user.microservice.repository.UserRepository;
import com.microservice.practical.demo.user.microservice.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private LoginUser user;
	
	
	private UserService userService;
	
	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService
			) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
			}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			this.user = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>());
		Authentication authenticate = authenticationManager.authenticate(token);

		return authenticate;

		// super.attemptAuthentication(request, response);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		
		String name = authResult.getName();
		Optional<RegisterUserEntity> userDetailsByUsername = userService.getUserDetailsByUsername(name);
		
			
		 String token = Jwts.builder()
		 .setSubject(userDetailsByUsername.get().getId()) 
		 .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong("3600000")))
		 .signWith(SignatureAlgorithm.HS512, "1nc4jRjdO5enfUc4loN3q7gEb8fhr9O" )
		 .compact(); 
		 
		 response.addHeader("token", token); 
		 response.addHeader("userId", userDetailsByUsername.get().getId());

		
		
		System.out.println("*********************************************");
		System.out.println("Successful Authentication");
		System.out.println("**********************************************");
		System.out.println("Generated JWT = " + token);
		
		//super.successfulAuthentication(request, response, chain, authResult);
	}

}
