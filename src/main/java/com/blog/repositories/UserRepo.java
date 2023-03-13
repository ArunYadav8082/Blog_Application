package com.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.User;
import com.blog.payloads.UserDto;

public interface UserRepo extends JpaRepository<User, Integer>
{

	User save(UserDto userDto);
	
	Optional<User> findByEmail(String email);

}
