package com.example.UserAuthentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserAuthentication.model.User;

public interface UserRepostiory extends JpaRepository<User, Long> {

	Optional<User> findByEmailId(String email);

}
