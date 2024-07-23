package com.example.UserAuthentication.IAuthService;

import com.example.UserAuthentication.exception.UserNotPresentException;

import org.antlr.v4.runtime.misc.Pair;
import org.springframework.util.MultiValueMap;

import com.example.UserAuthentication.exception.UserAlreadyExistException;
import com.example.UserAuthentication.model.User;

public interface IAuthService {
	
	public User signup(String email, String password) throws UserAlreadyExistException;
	
	public Pair<User, MultiValueMap<String, String>> login(String email, String password) throws UserNotPresentException;
	
}
  