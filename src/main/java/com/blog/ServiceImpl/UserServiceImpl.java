package com.blog.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.exceptions.ResourceNotFoundException;
import com.blog.config.Constant;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepository;
import com.blog.repositories.UserRepo;
import com.blog.sevices.UserService;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	/// update user
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
	
		 User user = this.userRepo.findById(userId)
		                     .orElseThrow(()->new ResourceNotFoundException("User", "Id" ,userId));
		 
		 user.setId(userDto.getId());
		 user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
			
		User saveUser = this.userRepo.save(user);
		UserDto dto = this.userToDto(saveUser);
		return dto;
	}

	@Override
	public UserDto getUserById(Integer Id) {
		
		 User user = this.userRepo.findById(Id)
                 .orElseThrow(()->new ResourceNotFoundException("User", "Id" ,Id));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		
		List<User> users = this.userRepo.findAll();
		List<UserDto> usersDto = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return usersDto;
	}

	@Override
	public void deleteUser(Integer Id) {
		
		 User user = this.userRepo.findById(Id)
                 .orElseThrow(()->new ResourceNotFoundException("User", "Id" ,Id));
		
		 this.userRepo.delete(user);
	}
	
	private User dtoToUser(UserDto userDto)
	{
		User user = this.modelMapper.map(userDto, User.class);
		
	/*	User user = new User();
		
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());*/
		
		return user;
	}
	
	public UserDto userToDto(User user)
	{
		UserDto dto = this.modelMapper.map(user,UserDto.class);
		
		/*UserDto dto = new UserDto();
		
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		dto.setAbout(user.getAbout());*/
		
		return dto;
	}
	
	// Register new User

	@Override
	public UserDto registerUser(UserDto userDto) {
	
		User user = this.modelMapper.map(userDto, User.class);
		
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		// roles
		
		Role role = this.roleRepo.findById(Constant.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		
		return this.modelMapper.map(newUser,UserDto.class);
	}

}
