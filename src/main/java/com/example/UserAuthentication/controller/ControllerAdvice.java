package com.example.UserAuthentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * If some exception is thrown from Service class then controller class can handle exception.
 * 
 * But If we throw some exception from controller class, 
 * then this global class will handle that exception.
 * 
 */

@RestControllerAdvice
public class ControllerAdvice {
	
		@ExceptionHandler({IllegalArgumentException.class, ArrayIndexOutOfBoundsException.class})
		public ResponseEntity<String> handleException(Exception ex){
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
}
