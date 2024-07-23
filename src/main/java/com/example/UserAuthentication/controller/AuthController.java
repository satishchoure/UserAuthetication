package com.example.UserAuthentication.controller;

import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserAuthentication.IAuthService.IAuthService;
import com.example.UserAuthentication.dto.LoginRequestDto;
import com.example.UserAuthentication.dto.SignupRequestDto;
import com.example.UserAuthentication.dto.UserDto;
import com.example.UserAuthentication.exception.UserAlreadyExistException;
import com.example.UserAuthentication.exception.UserNotPresentException;
import com.example.UserAuthentication.model.User;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	IAuthService authService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@PostMapping("/signup")
	public ResponseEntity<UserDto> Signup(@RequestBody SignupRequestDto signupRequestDto) throws UserAlreadyExistException {
		try {
			User user = authService.signup(signupRequestDto.getEmail(),signupRequestDto.getPassword());
			if(user == null) {
				throw new UserAlreadyExistException("User Already Present");
			}
			UserDto userdto = from(user);
			return new ResponseEntity<>(userdto, HttpStatus.CREATED);
		} 
		catch (UserAlreadyExistException ex) {
			/* 
			 * I am throwing excpeiton from contorller then who will handle this exception
			 * So I need Global Advice Controller.
			 */
			throw ex; 
			//return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
		
		Pair<User, MultiValueMap<String, String>> pair;
		try {
			pair = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
			User user = pair.a;
			if(user == null) {
				throw new UserNotPresentException("User Not Present");
			}
			/* Returning 2 values --> With Pair Class
			 * User and 
			 * the Header(Which includes token)
			 * */
			return new ResponseEntity<UserDto>(from(user), pair.b, HttpStatus.OK );
		} catch (UserNotPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
	}

	public UserDto from(User user) {
		UserDto userDto = new UserDto();
		userDto.setEmail(user.getEmail());
		userDto.setRoles(user.getRoles());
		return userDto;
	}
	
}
