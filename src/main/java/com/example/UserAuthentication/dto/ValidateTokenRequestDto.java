package com.example.UserAuthentication.dto;

import com.example.UserAuthentication.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(value = Include.NON_NULL)
public class ValidateTokenRequestDto {
	
	private String token;
	
	private long userId;
}
