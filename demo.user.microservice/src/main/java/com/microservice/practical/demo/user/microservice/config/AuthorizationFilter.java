package com.microservice.practical.demo.user.microservice.config;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.authenticationManager = authenticationManager;

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

		if ((request.getHeader(HttpHeaders.AUTHORIZATION)) != null) {

			UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			try {
				chain.doFilter(request, response);
			} catch (IOException | ServletException e) {
				System.out.println("####################################################");
				System.out.println("Authorzation FAILED");
				System.out.println("######################################################");
				e.printStackTrace();
			}
		}

		try {
			chain.doFilter(request, response);
		} catch (IOException | ServletException e) {
			System.out.println("####################################################");
			System.out.println("Authorzation FAILED");
			System.out.println("######################################################");
			e.printStackTrace();
		}

	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		String headers = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = headers.replace("Bearer", "");
		String userJwtDetails = Jwts.parser().setSigningKey("1nc4jRjdO5enfUc4loN3q7gEb8fhr9O").parseClaimsJws(token)
					.getBody().getSubject();
		return new UsernamePasswordAuthenticationToken(userJwtDetails, null, new ArrayList<>());
				
	}
}