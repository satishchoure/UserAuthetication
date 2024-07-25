package com.example.UserAuthentication.config;


import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;

@Configuration
public class AuthConfiguration {
	
	/*This is required becuase of below listed 2 reasons
	 * 1. We have added Spring Securtiy jar so all the pages will be authenticated by default so
	 * So we need to implment spring secuerity filter and In this filter We will tell Spring Securtiy
	 * Not to secure the login or signup page.
	 * 2.Here We are using BcryptPasswordEncode So we need to use spring Securtiy.
	 * */
	@Bean
	SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().disable();
		httpSecurity.csrf().disable();
		httpSecurity.authorizeHttpRequests(authorizeRequest -> authorizeRequest.anyRequest().permitAll());
		return httpSecurity.build();
	}
	
	// Intilized here in config class becuase We are using this secret key in many places like; login() and ValidateToken();
	@Bean
	SecretKey getSecretKey() {
	//Create Secret Key from Mac Algo to sign the token.
			MacAlgorithm signAlgo = Jwts.SIG.HS256;
			SecretKey secret = signAlgo.key().build();
			return secret;
	}
	
}
