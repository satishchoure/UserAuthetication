package com.example.UserAuthentication.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.UserAuthentication.exception.UserNotPresentException;
import com.example.UserAuthentication.model.User;
import com.example.UserAuthentication.repository.UserRepostiory;

public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
    private UserRepostiory userRepostiory;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> optionalUser = userRepostiory.findByEmailId(email);
		if(optionalUser.isPresent()) {
			throw new UsernameNotFoundException("bad credentials");
		}
	
		return new CustomUserDetails(optionalUser.get());
	}

}
