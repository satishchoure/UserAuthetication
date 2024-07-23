package com.example.UserAuthentication.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.UserAuthentication.IAuthService.IAuthService;
import com.example.UserAuthentication.exception.UserNotPresentException;
import com.example.UserAuthentication.exception.UserAlreadyExistException;
import com.example.UserAuthentication.model.User;
import com.example.UserAuthentication.repository.UserRepostiory;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;

public class AuthService implements IAuthService {
	
	@Autowired
    private UserRepostiory userRepostiory;
	
	
	@Autowired 
	private BCryptPasswordEncoder brcyptPasswordEncoder;
	
	
	@Override
	public User signup(String email, String password) throws UserAlreadyExistException {
		// TODO Auto-generated method stub
		Optional<User> optionalUser = userRepostiory.findByEmailId(email);
		if(optionalUser.isPresent()) {
			// We Should handle Exception in Controller,
			// because We return response to UI from Controller. 
			return null;
		}
		User user = new User();
		user.setEmail(email);
		// Encoding the password.
		user.setPassword(brcyptPasswordEncoder.encode(password));
		userRepostiory.save(user);
		return user;
	}

	@Override
	public Pair<User, MultiValueMap<String, String>> login(String email, String password) throws UserNotPresentException{
		// TODO Auto-generated method stub
		Optional<User> optionalUser = userRepostiory.findByEmailId(email);
		if(optionalUser.isEmpty()) {
			return null;
		}
		User user = optionalUser.get();
		String userEmail = user.getEmail();
		if(!userEmail.equals(email)   && !brcyptPasswordEncoder.matches(password, user.getPassword())) {
			return null;
		}
		//Create Claims which is nothing but your payload.
		Map<String, Object> userClaim = new HashMap<>();
		userClaim.put("UserId", user.getID());
		userClaim.put("Email", user.getEmail());
		userClaim.put("Roles", user.getRoles());
		userClaim.put("iat", System.currentTimeMillis()); //IAT -> IssuedAt
		userClaim.put("expiry", System.currentTimeMillis()+ 1000000); //IAT -> IssuedAt
		
		//Create Secret Key from Mac Algo to sign the token.
		MacAlgorithm signAlgo = Jwts.SIG.HS256;
		SecretKey secret = signAlgo.key().build();
		
		//Create JWT Token
		String token = Jwts.builder().setClaims(userClaim).signWith(secret).compact();
		
		//Create MultiValueMap for the token.
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		
		return new Pair<User, MultiValueMap<String, String>>(user, headers);
	}	
}
