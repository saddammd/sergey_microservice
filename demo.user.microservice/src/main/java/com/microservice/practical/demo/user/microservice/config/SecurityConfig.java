package com.microservice.practical.demo.user.microservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.microservice.practical.demo.user.microservice.service.UserService;

@Configuration
@EnableWebSecurity(debug=true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements EnvironmentAware{
	
	@Autowired
	private UserService userService;
	
	private Environment environment;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").hasIpAddress("192.168.1.7")
		.and()
		.addFilter(getAuthenticationFilter())
		.addFilter(getAuthorizationFilter());
		http.headers().frameOptions().disable();
		super.configure(http);
	}
	

	@Override
	public void setEnvironment(Environment environment) {
		
		this.environment = environment;
		
	}
	
@Bean	
public BCryptPasswordEncoder bCryptPasswordEncoder() {
return new BCryptPasswordEncoder();
}
	
@Override
@Bean(name=BeanIds.AUTHENTICATION_MANAGER)
public AuthenticationManager authenticationManagerBean() throws Exception {
	return super.authenticationManagerBean();
}
	
private AuthenticationFilter getAuthenticationFilter() throws Exception {
	AuthenticationFilter filter = new AuthenticationFilter(authenticationManagerBean(), userService,
			this.environment);
	filter.setFilterProcessesUrl("/login");
	return filter;
}

private AuthorizationFilter getAuthorizationFilter() throws Exception {
	
	AuthorizationFilter authenticationFilter = new AuthorizationFilter(authenticationManagerBean());
	return authenticationFilter;
	
}




}
