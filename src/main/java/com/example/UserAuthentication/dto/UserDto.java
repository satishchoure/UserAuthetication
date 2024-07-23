package com.example.UserAuthentication.dto;

import java.util.HashSet;
import java.util.Set;
import com.example.UserAuthentication.model.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
	
	private String email;
	
	private Set<Role> roles = new HashSet<>();
}
