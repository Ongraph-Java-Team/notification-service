package com.ongraph.notificationserviceapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ongraph.commonserviceapp.security.filter.AuthEntryPoint;
import com.ongraph.commonserviceapp.security.filter.AuthTokenFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
	
	private AuthTokenFilter authTokenFilter;
	
	private AuthEntryPoint unauthorizedHandler;
	
	
	public WebSecurityConfig(AuthTokenFilter authTokenFilter,
			AuthEntryPoint authEntryPoint) {
		this.authTokenFilter=authTokenFilter;
		this.unauthorizedHandler=authEntryPoint;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests(authorizeHttpRequest->
			authorizeHttpRequest
			.anyRequest().authenticated()
		)
		.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
		.httpBasic(withDefaults())
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
		return http.build();
		
	}

}
