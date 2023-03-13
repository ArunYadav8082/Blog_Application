package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.sevices.UserService;

@RestController
@RequestMapping("/api/users")
public class UserControllers {

	@Autowired
	private UserService userService;
	
	// create user
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto)
	{
		System.out.println("post Handler");
		UserDto userDto2 = this.userService.createUser(userDto);
		
		return new ResponseEntity<>(userDto2 , HttpStatus.CREATED);
	}
	
	// update user
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser( @Valid @RequestBody UserDto userDto ,
			                                    @PathVariable("userId") Integer uId)
	{
		System.out.println("This is updated Handler");
		UserDto updateUser = this.userService.updateUser(userDto, uId);
		return ResponseEntity.ok(updateUser);
	}
	
	// delete user  by id
	
	// Admin deleted password abhay
	// Normal password welcome123 username bhupesh@gmail.com
	
	@PreAuthorize("hasRole('NORMAL')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable ("userId") Integer uId)
	{
		this.userService.deleteUser(uId);
		
		return new ResponseEntity(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
	
	// get All users
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> findAllUser()
	{
		return ResponseEntity.ok(this.userService.getAllUser());
		
	}
	
	// get user by user Id
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> findUserById(@PathVariable Integer userId)
	{
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}













