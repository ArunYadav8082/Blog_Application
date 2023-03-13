   package com.blog;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blog.config.Constant;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepository;






@SpringBootApplication
public class BootjpaApplication {

	@Autowired
	private RoleRepository roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BootjpaApplication.class, args);
	}
		
	@Bean
	public ModelMapper modelMapper()
	{
			try {
				
				Role role = new Role();
				role.setId(Constant.ADMIN_USER);
				role.setName("ROLE_ADMIN");
				
				Role role1 = new Role();
				role1.setId(Constant.NORMAL_USER);
				role1.setName("ROLE_NORMAL");
				
				List<Role> list = new ArrayList<>();
				list.add(role);
				list.add(role1);
				
				this.roleRepo.saveAll(list);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			return new ModelMapper();
	}
	

	
	
	
		
		 
	}


