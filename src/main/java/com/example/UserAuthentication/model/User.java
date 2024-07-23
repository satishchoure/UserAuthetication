package com.example.UserAuthentication.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseModel{
	
	private String email;
	private String password;
	
	// Why Set instead of list, because the roles will be unique and 
	// For unique roles We will use HashSet Data Structure
	// And new HashSet why? Becuase if user do not have any role then there should be empty HashSet.
	@ManyToMany
	private Set<Role> roles = new HashSet<>();

}
