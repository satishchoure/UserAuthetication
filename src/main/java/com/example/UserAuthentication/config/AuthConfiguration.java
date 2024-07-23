package com.example.UserAuthentication.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
	
}
