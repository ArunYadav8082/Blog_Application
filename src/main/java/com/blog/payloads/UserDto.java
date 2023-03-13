package com.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size( min=4, max=10, message = "username is min of 4 characters and max of 10 characters")
	private String name;
	@Email(message = "Email is not valid")
	private String email;
	@NotEmpty
	@Size(min=4, max=10,message = "password is min of 4 and max of  8" )
	@JsonIgnoreProperties
	private String password;
	
	@NotEmpty(message = "About user is Null i.e. not valid")
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();

}
