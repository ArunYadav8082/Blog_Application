package com.blog.sevices;

import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerUser(UserDto userDto);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user , Integer Id);
	
	UserDto getUserById(Integer Id);
	
	List<UserDto> getAllUser();
	
	void deleteUser(Integer Id);

}
